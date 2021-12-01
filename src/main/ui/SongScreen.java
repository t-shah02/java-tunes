package ui;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import model.Song;

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
 * Represents the single song/track player screen JFrame
 */
public class SongScreen extends JFrame {
    private static final int WINDOW_WIDTH = 520;
    private static final int WINDOW_HEIGHT = 360;

    private MusicControls mc;
    private static MediaPlayer mp;
    private HomeScreen hs;
    private Song song;
    private String name;
    private boolean sliderUpdating;
    private boolean songPaused;

    private JSlider seeker;
    private JLabel currentSongTime;
    private JButton pausePlayButton;
    private JButton restartButton;

    // REQUIRES: valid file paths and no thread interruptions
    // MODIFIES: this
    // EFFECTS: creates a new Song Screen JFrame
    public SongScreen(HomeScreen hs, Song s, String rawName) throws IOException, InterruptedException {
        this.hs = hs;
        this.song = s;
        name = rawName;
        mc = new MusicControls();
        Media source = new Media(new File(s.getSongPath()).toURI().toString());
        mp = new MediaPlayer(source);
        mc.play(mp);
        songPaused = false;
        Thread.sleep(300);
        makeFrame();
        updateSongTime();

    }

    // EFFECTS: finalizes the frame (window) to display to the user
    public void makeFrame() throws IOException {
        setUpPlayer();
        initializeFrame();
        addListeners();
    }

    // MODIFIES: this
    // EFFECTS: adds a windown listener to listen to window closing (to stop the currently playing audio)
    public void addWindowListeners() {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                mc.stop(mp);
                hs.setActiveWindow(false);
                super.windowClosed(e);
            }
        });
    }

    // EFFECTS: adds all the slider listeners for this JFrame
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

    // EFFECTS: adds all the listeners for this JFrame
    public void addListeners() {
        addWindowListeners();
        addSliderListeners();
        addButtonListeners();
    }

    // MODIFIES: this
    // EFFECTS: handles the pause/play button action listener
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
                        return;
                    }
                } else {
                    songPaused = true;
                    mc.pause(mp);
                    try {
                        pausePlayButton.setIcon(getPauseOrPlayIcon());
                    } catch (IOException ex) {
                        return;
                    }
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: handles the restart button action listener
    public void handleRestart() {
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mc.restart(mp);
            }
        });
    }

    // EFFECTS: adds all the button listeners to this JFrame
    public void addButtonListeners() {
        handlePause();
        handleRestart();
    }

    // REQUIRES: valid image file paths
    // EFFECTS: returns an ImageIcon, based on the current value of the songPaused boolean
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
    // EFFECTS: makes the initial attributes of this JFrame
    public void initializeFrame() {
        this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.setResizable(false);
        this.setTitle("Song: " + name);
        this.pack();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    // REQUIRES: valid file paths
    // MODIFIES: this
    // EFFECTS: sets up the playerBox layout components
    public void setUpPlayer() throws IOException {
        Box playerBox = Box.createVerticalBox();
        BufferedImage songImage = ImageIO.read(new File("./data/icons/default_song.png"));
        JLabel defaultSongImage = new JLabel(new ImageIcon(songImage));
        seeker = new JSlider(JSlider.HORIZONTAL, 0, (int) mp.getTotalDuration().toSeconds(), 0);
        currentSongTime = new JLabel();
        currentSongTime.setFont(new Font("Arial Black",Font.PLAIN,20));
        pausePlayButton = new JButton(new ImageIcon(ImageIO.read(new File("./data/icons/pause_icon.png"))));
        restartButton = new JButton(new ImageIcon(ImageIO.read(new File("./data/icons/restart_icon.png"))));
        JPanel musicControls = new JPanel();
        musicControls.setLayout(new FlowLayout());
        musicControls.add(pausePlayButton);
        musicControls.add(restartButton);
        playerBox.add(defaultSongImage);
        playerBox.add(seeker);
        playerBox.add(musicControls);
        playerBox.add(currentSongTime);
        this.add(playerBox);
        defaultSongImage.setAlignmentX(Component.CENTER_ALIGNMENT);
        currentSongTime.setAlignmentX(Component.CENTER_ALIGNMENT);
        seeker.setAlignmentX(Component.CENTER_ALIGNMENT);
        pausePlayButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    // MODIFIES: this
    // EFFECTS: updates the current song time, every 1 second of audio playback
    public void updateSongTime() {
        Runnable timeStamp = new Runnable() {
            @Override
            public void run() {
                sliderUpdating = true;
                currentSongTime.setText(mc.timeStampOfCurrentTime(mp));
                seeker.setValue((int) mp.getCurrentTime().toSeconds());
                EventQueue.invokeLater(
                        () -> {
                            sliderUpdating = false;
                        });
            }
        };
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(timeStamp, 0, 1, TimeUnit.SECONDS);
    }


}

