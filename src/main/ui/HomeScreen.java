package ui;


import model.*;
import model.Event;
import persistence.PlaylistLoader;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * Represents the home screen JFrame (screen shown on the application's launch)
 */
public class HomeScreen extends JFrame {
    // constant values
    private static final int WINDOW_WIDTH = 750;
    private static final int WINDOW_HEIGHT = 600;

    // Swing element fields
    private JLabel appDescription;
    private JLabel aboutMe;
    private JLabel termProject;
    private JLabel musicIcon;
    private JMenuBar appMenuBar;
    private JLabel singleSongSelectDesc;
    private JButton songSelect;
    private JLabel playlistSelectDesc;
    private JButton playlistSelect;
    private Box homeBox;
    private JList singleTrackList;
    private JList playlistList;

    // Model/persistance/Misc fields
    private PlaylistLoader loader;
    private SongDataStorage sds;
    private ArrayList<Playlist> playlists;

    // window references
    private HomeScreen homeScreen = this;


    // booleans
    private boolean activeWindow;

    // Constructor for the home screen of the application
    public HomeScreen() throws IOException {
        instanitateObjects();
        activeWindow = false;
        createHomeScreenFrame();
    }

    // REQUIRES: all the playlist json data must exist in a valid, readable file
    // MODIFIES: this
    // EFFECtS: instantiates the peristence and model package fields for the HomeScreen class
    public void instanitateObjects() throws IOException {
        loader = new PlaylistLoader();
        sds = new SongDataStorage();
        playlists = loader.loadAllPlaylists();
        for (Playlist p : playlists) {
            for (Song song : p.getSongs()) {
                MusicPlayerEvents.loadSongFromPlaylistLog(p,song);
            }
            MusicPlayerEvents.loadPlaylistLog(p);
        }
    }

    // REQUIRES: valid file paths
    // EFFECTS: creates and displays the final home screen frame to the user
    public void createHomeScreenFrame() throws IOException {
        makeMenu();
        makeHomeBox();
        addListeners();
        initializeFrame();
    }

    // REQUIRES: valid image file paths
    // MODIFIES: this
    // EFFECTS: initializes the basic frame attributes
    public void initializeFrame() throws IOException {
        this.setTitle("Tunes.Java");
        this.setResizable(false);
        this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(ImageIO.read(new File("./data/icons/app_logo.png")));
        this.pack();
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.setVisible(true);
    }

    // EFFECTS: makes the final Box component and adds it to the box layout of the home screen
    public void makeHomeBox() throws IOException {
        homeBox = Box.createVerticalBox();
        initializeTitleLabels();
        addTitleLabels();
        addSingleSongElements();
        addPlaylistElements();
        this.add(homeBox);
    }

    // MODIFIES: this
    // EFFECTS: adds the title labels to the Box component
    public void addTitleLabels() {
        homeBox.add(appDescription);
        homeBox.add(aboutMe);
        homeBox.add(termProject);
        homeBox.add(musicIcon);
        appDescription.setAlignmentX(Component.CENTER_ALIGNMENT);
        aboutMe.setAlignmentX(Component.CENTER_ALIGNMENT);
        termProject.setAlignmentX(Component.CENTER_ALIGNMENT);
        musicIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    // REQUIRES: valid image file paths
    // MODIFIES: this
    // EFFECTS: makes the main title Jlabels of the home screen
    public void initializeTitleLabels() throws IOException {
        appDescription = new JLabel("Tunes.java - Music Player in Java Swing!");
        appDescription.setFont(new Font("Impact", Font.BOLD, 30));
        appDescription.setForeground(Color.RED);
        aboutMe = new JLabel("Designed by: Tanish Shah");
        aboutMe.setFont(new Font("Impact", Font.PLAIN, 15));
        termProject = new JLabel("Term project for CPSC 210");
        termProject.setFont(new Font("Impact", Font.PLAIN, 15));
        ImageIcon musicIconImage = new ImageIcon(ImageIO.read(new File("./data/icons/app_logo.png")));
        musicIcon = new JLabel(musicIconImage);

    }

    // MODFIIES: this
    // EFFECTS: makes the home screen menu bar
    public void makeMenu() throws IOException {
        appMenuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem openSong = new JMenuItem("Open song audio source");
        file.add(openSong);
        JMenu playlist = new JMenu("Playlist");
        JMenuItem createPlaylist = new JMenuItem("Create");
        JMenuItem deletePlaylist = new JMenuItem("Delete");
        JMenuItem editPlaylist = new JMenuItem("Edit");
        openSong.setIcon(new ImageIcon(ImageIO.read(new File("./data/icons/open_song_icon.png"))));
        createPlaylist.setIcon(new ImageIcon(ImageIO.read(new File("./data/icons/add_icon.png"))));
        deletePlaylist.setIcon(new ImageIcon(ImageIO.read(new File("./data/icons/minus_icon.png"))));
        editPlaylist.setIcon(new ImageIcon(ImageIO.read(new File("./data/icons/edit_icon.png"))));
        playlist.add(createPlaylist);
        playlist.add(editPlaylist);
        playlist.add(deletePlaylist);
        appMenuBar.add(file);
        appMenuBar.add(playlist);
        appMenuBar.setBackground(Color.GREEN);
        this.setJMenuBar(appMenuBar);

    }

    // MODIFIES: this
    // EFFECTS: adds all the single song gui elements to the screen
    public void addSingleSongElements() {
        singleSongSelectDesc = new JLabel("Select and play a single track/song found in the data/audio directory:");
        homeBox.add(singleSongSelectDesc);
        singleSongSelectDesc.setAlignmentX(Component.CENTER_ALIGNMENT);
        SongDataStorage sds = new SongDataStorage();
        List<String> audioContents = sds.getAudioContents();
        DefaultListModel model = new DefaultListModel();
        for (String fileName : audioContents) {
            model.addElement(fileName);
        }
        JScrollPane blueScroller = new JScrollPane();
        singleTrackList = new JList(model);
        blueScroller.setViewportView(singleTrackList);
        blueScroller.setBorder(new LineBorder(Color.BLUE, 5));
        JPanel singleSongPanel = new JPanel();
        songSelect = new JButton("Play the selected song!");
        singleSongPanel.add(blueScroller);
        singleSongPanel.add(songSelect);
        homeBox.add(singleSongPanel);

    }

    // MODIFIES: this
    // EFFECTS: adds all the playlist gui elements
    public void addPlaylistElements() {
        List<String> playlistNames = new ArrayList<String>();
        for (Playlist p : playlists) {
            playlistNames.add(p.getPlaylistName());
        }
        playlistSelectDesc = new JLabel("Select and load a playlist saved in the data/playlist_data directory:");
        homeBox.add(playlistSelectDesc);
        playlistSelectDesc.setAlignmentX(Component.CENTER_ALIGNMENT);
        DefaultListModel model = new DefaultListModel();
        for (String name : playlistNames) {
            model.addElement(name);
        }
        playlistList = new JList(model);
        JScrollPane blueScroller = new JScrollPane();
        blueScroller.setViewportView(playlistList);
        blueScroller.setBorder(new LineBorder(Color.BLUE, 5));
        playlistSelect = new JButton("Load the selected playlist!");
        JPanel playlistPanel = new JPanel();
        playlistPanel.add(blueScroller);
        playlistPanel.add(playlistSelect);
        homeBox.add(playlistPanel);
    }

    // EFFECTS: adds all the general listeners of this home screen frame
    public void addListeners() {
        addButtonListeners();
        addMenuItemListeners();
        addWindowListeners();
    }


    // EFFECTS: handles the window closing event to print the event log (when the application exits)
    public void addWindowListeners() {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                EventLog logs = EventLog.getInstance();
                for (Event event : logs) {
                    System.out.println(event.toString());
                }
                super.windowClosed(e);
            }
        });
    }

    // EFFECTS: adds all the menu item listeners for home screen JMenu
    public void addMenuItemListeners() {
        JMenuItem open = appMenuBar.getMenu(0).getItem(0);
        JMenuItem createPlaylist = appMenuBar.getMenu(1).getItem(0);
        JMenuItem editPlaylist = appMenuBar.getMenu(1).getItem(1);
        JMenuItem removePlaylist = appMenuBar.getMenu(1).getItem(2);
        addOpenSongAction(open);
        addCreatePlaylistAction(createPlaylist);
        addEditPlaylistAction(editPlaylist);
        addRemovePlaylistAction(removePlaylist);
    }

    // EFFECTS: adds the action listener for the file/open menu item
    public void addOpenSongAction(JMenuItem open) {
        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openSongAction();
            }
        });
    }

    // EFFECTS: adds the action listener for the playlist create menu item
    public void addCreatePlaylistAction(JMenuItem createPlaylist) {
        createPlaylist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createPlaylistAction();
            }
        });
    }

    // EFFECTS: adds the action listener for the playlist edit menu item
    public void addEditPlaylistAction(JMenuItem editPlaylist) {
        editPlaylist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editPlaylistAction();
            }
        });
    }

    // EFFECTS: adds the action listener for the playlist remove menu item
    public void addRemovePlaylistAction(JMenuItem removePlaylist) {
        removePlaylist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removePlaylistAction();
            }
        });
    }


    // EFFECTS: calls the handleFileSelect() helper method
    public void openSongAction() {
        handleFileSelect();
    }

    // EFFECTS: makes a new playlist editor screen
    public void editPlaylistAction() {
        new PlaylistEditorScreen(homeScreen, playlists);
    }

    // REQUIRES: all song and image data to have valid/readable file paths
    // EFFECTS: makes a new playlist creator screen
    public void createPlaylistAction() {
        try {
            new PlaylistCreatorScreen(homeScreen);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // EFFECTS: creates a new playlist remover screen
    public void removePlaylistAction() {
        new PlaylistRemoverScreen(homeScreen, playlists);
    }

    // MODIFIES: this
    // EFFECTS: handles the song mp3 file select in the jmenubar to open songs from the user's system
    public void handleFileSelect() {
        if (!activeWindow) {
            FileNameExtensionFilter ff = new FileNameExtensionFilter(null, "mp3");
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(ff);
            int response = chooser.showOpenDialog(null);
            if (response == JFileChooser.APPROVE_OPTION) {
                File file = new File(chooser.getSelectedFile().getAbsolutePath());
                String path = file.getPath();
                String rawName = file.getName();
                activeWindow = true;
                try {
                    new SongScreen(homeScreen, new Song(path), rawName);
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    // EFFECTS: adds all the button listeners for the homescreen frame
    public void addButtonListeners() {
        addSongSelectListener();
        addPlaylistListener();
    }

    // REQUIRES: valid song audio files and no threading interruptions
    // MODIFIES: this
    // EFFECTS: handles song select button action listeners, and loads a song player screen (if selected properly)
    public void addSongSelectListener() {
        songSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!activeWindow) {
                    int selectedIndex = singleTrackList.getSelectedIndex();
                    if (selectedIndex != -1) {
                        tryOpenSongScreen(selectedIndex);
                    } else {
                        JOptionPane.showMessageDialog(homeScreen,"Please select a song first",
                                "No selection error",JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(homeScreen,"Please close the current playback window first",
                            "Active audio error",JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    // EFFECTS: opens a new song screen window
    public void tryOpenSongScreen(int selectedIndex) {
        DefaultListModel model = (DefaultListModel) singleTrackList.getModel();
        String songPath = "./data/audio/" + (String) model.getElementAt(selectedIndex);
        String rawName = (String) model.getElementAt(selectedIndex);
        Song song = new Song(songPath);
        try {
            new SongScreen(homeScreen, song, rawName);
            MusicPlayerEvents.openSingleSongLog(song);
            activeWindow = true;
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    // REQUIRES: valid playlist data files and no threading interruptions
    // MODIFIES: this
    // EFFECTS: handles playlist select button action listeners,
    //          and loads a playlist player screen (if selected properly)
    public void addPlaylistListener() {
        playlistSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = playlistList.getSelectedIndex();
                if (!activeWindow) {
                    if (selectedIndex != -1) {
                        try {
                            tryOpenPlaylist(selectedIndex);
                        } catch (IOException | InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        JOptionPane.showMessageDialog(homeScreen,"Please select a playlist first",
                                "No selection error",JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(homeScreen,"Please close the current playback window first",
                            "Active audio error",JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: trys to open a new playlist window
    public void tryOpenPlaylist(int selectedIndex) throws IOException, InterruptedException {
        Playlist p = playlists.get(selectedIndex);
        new PlaylistScreen(homeScreen, p);
        MusicPlayerEvents.openPlaylistLog(p);
        activeWindow = true;
    }


    // MODIFIES: this
    // EFFECTS: updates activeWindow to the value of active, to indicate a song/playlist is active in a new window
    public void setActiveWindow(boolean active) {
        activeWindow = active;
    }


    // REQUIRES: all playlists data files are readable and valid
    // MODIFIES: this
    // EFFECTS: programatically updates the playlistList JList component, given playlist changes in other windows
    public void updatePlaylists() throws IOException {
        playlists = loader.loadAllPlaylists();
        DefaultListModel model = (DefaultListModel) playlistList.getModel();
        model.removeAllElements();
        for (Playlist p : playlists) {
            model.addElement(p.getPlaylistName());
        }

    }


}
