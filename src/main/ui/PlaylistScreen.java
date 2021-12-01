package ui;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import model.Playlist;
import model.Song;
import persistence.PlaylistLoader;
import persistence.PlaylistWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/*
 * Represents the playlist player JFrame
 */
public class PlaylistScreen extends JFrame {

    // constants
    private static final int WINDOW_WIDTH = 520;
    private static final int WINDOW_HEIGHT = 375;
    private static final int PLAYLIST_COVER_SIZE = 200;

    // object fields
    private MusicControls mc;
    private static MediaPlayer mp;
    private HomeScreen hs;
    private Playlist playlist;
    private int currentSongIndex;
    private int secondsTime;
    private boolean sliderUpdating;
    private boolean songPaused;
    private PlaylistLoader loader;
    private PlaylistWriter writer;
    private String coverImagePath;

    // GUI fields
    private JSlider seeker;
    private JLabel currentSongTime;
    private JButton pausePlayButton;
    private JButton restartButton;
    private JButton nextSong;
    private JButton previousSong;
    private JLabel currentSongName;
    private JLabel playlistCover;
    private Box playerBox;

    // REQUIRES: valid file paths and no thread interuptions
    // MODIFIES: this
    // EFFECTS: makes a new PlaylistScreen JFrame to display to the user
    public PlaylistScreen(HomeScreen hs, Playlist playlist) throws IOException, InterruptedException {
        this.hs = hs;
        this.playlist = playlist;
        initializeFields();
        loadLastSong();
        Thread.sleep(300);
        makeFrame();
        updateSongTime();

    }

    // MODIFIES: this
    // EFFECTS: initializes the fields of this class
    public void initializeFields() {
        mc = new MusicControls();
        loader = new PlaylistLoader();
        songPaused = false;
    }

    // EFFECTS: returns the name of the current song that is playing within the active playlist
    public String getCurrentSongName() {
        Song current = playlist.getSongs().get(currentSongIndex);
        String songPath = current.getSongPath();
        String[] songPathSplit = songPath.split("/");
        return songPathSplit[songPathSplit.length - 1];
    }

    // REQUIRES: valid file paths and no thread interuptions
    // MODIFIES: this
    // EFFECTS: loads information about the last song in this playlist (song index and its last played time)
    public void loadLastSong() throws IOException, InterruptedException {
        currentSongIndex = loader.getSongIndexFromFile(playlist);
        secondsTime = loader.getTimeStampFromFile(playlist);
        coverImagePath = loader.getCoverPathFromFile(playlist);
        Song s = playlist.getSongs().get(currentSongIndex);
        Media source = new Media(new File(s.getSongPath()).toURI().toString());
        mp = new MediaPlayer(source);
        Thread.sleep(200);
        mc.seek(mp,secondsTime);
        mc.play(mp);
    }

    // EFFECTS: makes the final frame that will be visible to the user
    public void makeFrame() throws IOException {
        setUpPlayer();
        addListeners();
        initializeFrame();
    }

    // REQUIRES: a valid playlist file to write (save) to
    // MODIFIES: this
    // EFFECTS: adds the window listeners, to save the playlist song index/timestamp to file on window close
    public void addWindowListeners() {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                int currentTimeInSeconds = (int) mp.getCurrentTime().toSeconds();
                try {
                    writer = new PlaylistWriter(playlist,currentSongIndex,currentTimeInSeconds,coverImagePath);
                    writer.writeToFile();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                mp.dispose();
                hs.setActiveWindow(false);
                super.windowClosed(e);
            }
        });
    }

    // EFFECTS: adds the listeners for the seek bar, to seek through the mp3 file playing
    public void addSliderListeners() {
        seeker.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (!sliderUpdating) {
                    int seconds = seeker.getValue();
                    mc.seek(mp, seconds);
                }

            }
        });
    }

    // EFFECTS: adds all the general listeners to this frame
    public void addListeners() {
        addWindowListeners();
        addSliderListeners();
        addButtonListeners();
    }

    // MODIFIES: this
    // EFFECTS: handles the play/pause button action listener
    public void handlePause() {
        pausePlayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (songPaused) {
                    songPaused = false;
                    mc.play(mp);
                    try {
                        pausePlayButton.setIcon(getPauseOrPlayIcon());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    songPaused = true;
                    mc.pause(mp);
                    try {
                        pausePlayButton.setIcon(getPauseOrPlayIcon());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: handle the restart song button action listener
    public void handleRestart() {
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mc.restart(mp);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: increments or decrements the current song index in the playlist by 1, depending on decreasing's value
    public void updateSongIndex(boolean decreasing) {
        int playlistSize = playlist.getSongs().size();
        if (!decreasing) {
            if (currentSongIndex + 1 >= playlistSize) {
                currentSongIndex = 0;
            } else {
                currentSongIndex += 1;
            }
        } else {
            if (currentSongIndex - 1 < 0) {
                currentSongIndex = playlistSize - 1;
            } else {
                currentSongIndex -= 1;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: handles the next song button action listener
    public void handleNextSong() {
        nextSong.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mc.stop(mp);
                updateSongIndex(false);
                try {
                    updateTrack();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    // REQUIRES: valid no threading interruptions
    // MODIFIES: this
    // EFFECTS: when the user goes to the next/previous song, updates the label, audio source, and seeker values
    public void updateTrack() throws InterruptedException {
        currentSongName.setText(getCurrentSongName());
        Song nextSong = playlist.getSongs().get(currentSongIndex);
        Media source = new Media(new File(nextSong.getSongPath()).toURI().toString());
        mp = new MediaPlayer(source);
        mc.play(mp);
        Thread.sleep(200);
        DefaultBoundedRangeModel model = new DefaultBoundedRangeModel(0,1,0,
                (int) mp.getTotalDuration().toSeconds());

        seeker.setModel(model);
    }

    // MODIFIES: this
    // EFFECTS: handles the previous song button action listener
    public void handlePreviousSong() {
        previousSong.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mc.stop(mp);
                updateSongIndex(false);
                try {
                    updateTrack();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    // EFFECTS: adds all the button listeners that belong to this JFrame respecitvely
    public void addButtonListeners() {
        handlePause();
        handleRestart();
        handleNextSong();
        handlePreviousSong();
    }

    // REQUIRES: valid image file paths
    // EFFECTS: returns the corresponding play/pause icon, depending on the value of songPaused boolean
    public ImageIcon getPauseOrPlayIcon() throws IOException {
        if (songPaused) {
            BufferedImage pausedImage = ImageIO.read(new File("./data/icons/play_icon.png"));
            return new ImageIcon(pausedImage);
        } else {
            BufferedImage playImage = ImageIO.read(new File("./data/icons/pause_icon.png"));
            return new ImageIcon(playImage);
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes the basic attributes of this JFrame
    public void initializeFrame() {
        this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.setResizable(false);
        this.setTitle("Playlist: " + playlist.getPlaylistName());
        this.pack();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: instnatiates the GUI components of the playlist player
    public void makePlayerElements() throws IOException {
        playerBox = Box.createVerticalBox();
        seeker = new JSlider(JSlider.HORIZONTAL, 0, (int) mp.getTotalDuration().toSeconds(), 0);
        currentSongTime = new JLabel();
        currentSongTime.setFont(new Font("Arial Black",Font.PLAIN,20));
        pausePlayButton = new JButton(new ImageIcon(ImageIO.read(new File("./data/icons/pause_icon.png"))));
        restartButton = new JButton(new ImageIcon(ImageIO.read(new File("./data/icons/restart_icon.png"))));
        nextSong = new JButton(new ImageIcon(ImageIO.read(new File("./data/icons/next_icon.png"))));
        previousSong = new JButton(new ImageIcon(ImageIO.read(new File("./data/icons/previous_icon.png"))));
        currentSongName = new JLabel(getCurrentSongName());
        makeCoverImage();
    }

    // REQUIRES: valid file paths
    // MODIFIES: this
    // EFFECTS: makes the playlist cover image visually on the JFrame
    public void makeCoverImage() throws IOException {
        BufferedImage coverBufferImage = ImageIO.read(new File(coverImagePath));
        playlistCover = new JLabel(new ImageIcon(coverBufferImage));
        playerBox.add(playlistCover);
        Image reScaledCover = coverBufferImage.getScaledInstance(PLAYLIST_COVER_SIZE,PLAYLIST_COVER_SIZE,
                Image.SCALE_SMOOTH);
        playlistCover.setIcon(new ImageIcon(reScaledCover));
    }

    // MODIFIES: this
    // EFFECTS: adds all the GUI components to the player box component
    public void addToPlayerBox() {
        playlistCover.setAlignmentX(Component.CENTER_ALIGNMENT);
        playerBox.add(currentSongName);
        currentSongName.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPanel musicControls = new JPanel();
        musicControls.add(previousSong);
        musicControls.add(pausePlayButton);
        musicControls.add(nextSong);
        musicControls.add(restartButton);
        playerBox.add(seeker);
        playerBox.add(musicControls);
        playerBox.add(currentSongTime);
        currentSongTime.setAlignmentX(Component.CENTER_ALIGNMENT);
        seeker.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    // REQUIRES: valid file paths
    // MODIFIES: this
    // EFFECTS: finishes the setup of playlist player Box component and adds it to the this JFrame
    public void setUpPlayer() throws IOException {
        makePlayerElements();
        addToPlayerBox();
        this.add(playerBox);
    }

    // REQUIRES: no thread interruptions
    // EFFECTS: calls the getSonTimeInRealTime() method periodically every 1 second
    public void updateSongTime() {
        Runnable updater = new Runnable() {
            @Override
            public void run() {
                try {
                    getSongTimeInRealTime();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(updater, 0, 1, TimeUnit.SECONDS);
    }

    // REQUIRES: no thread interruptions
    // MODIFIES: this
    // EFFECTS: updates the timestamp label of the current song that is playing
    public void getSongTimeInRealTime() throws InterruptedException {
        sliderUpdating = true;
        currentSongTime.setText(mc.timeStampOfCurrentTime(mp));
        seeker.setValue((int) mp.getCurrentTime().toSeconds());
        EventQueue.invokeLater(
                () -> {
                    sliderUpdating = false;
                });
    }



}
