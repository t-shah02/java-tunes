package ui;


import model.MusicPlayerEvents;
import model.Playlist;
import model.Song;
import model.SongDataStorage;
import persistence.PlaylistWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * Represents the playlist creator screen JFrame
 */
public class PlaylistCreatorScreen extends JFrame {

    // constant values
    private static final int WINDOW_WIDTH = 900;
    private static final int WINDOW_HEIGHT = 750;
    private static final String DEFAULT_COVER_PATH = "./data/playlist_covers/default_cover.png";
    private static final int PLAYLIST_COVER_SIZE = 200;


    private SongDataStorage sds;
    private String selectedCoverPath;
    private PlaylistWriter writer;
    private HomeScreen reference;

    private JList songList;
    private JPanel namePanel;
    private JPanel songSelectionPanel;
    private JList addedSongList;
    private JPanel imagePanel;
    private JLabel selectedCoverImage;
    private JList allCovers;
    private JButton resetToDefaultCover;
    private JButton addPlaylist;
    private JTextField nameField;
    private Box creatorBox;

    // REQUIRES: valid file paths
    // MODIFIES: this
    // EFFECTS: makes a new playlist creator screen
    public PlaylistCreatorScreen(HomeScreen parent) throws IOException {
        this.reference = parent;
        setObjects();
        makeElements();
        addElements();
        addListeners();
        initializeFrame();
    }

    // MODIFIES: this
    // EFFECTS: instanitates important fields of this class
    public void setObjects() {
        sds = new SongDataStorage();
        selectedCoverPath = DEFAULT_COVER_PATH;
    }

    // MODIFIES: this
    // EFFECTS: creates the intiial attributes of this JFrame
    public void initializeFrame() {
        this.setResizable(false);
        this.setPreferredSize(new Dimension(WINDOW_WIDTH,WINDOW_HEIGHT));
        this.setTitle("Playlist builder");
        this.setLayout(new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS));
        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    // REQUIRES: valid file paths
    // EFFECTS: makes all the GUI elements of the playlist builder screen
    public void makeElements() throws IOException {
        addNameElements();
        addSongElements();
        addImageElements();
    }

    // MODIFIES: this
    // EFFECTS: adds the playlist name fields components
    public void addNameElements() {
        namePanel = new JPanel();
        JLabel playlistName = new JLabel("Enter a playlist name:");
        nameField = new JTextField("A descriptive playlist name!");
        namePanel.add(playlistName);
        namePanel.add(nameField);
    }

    // MODIFIES: this
    // EFFECTS: makes the song selection and added song Jlists
    public void addSongElements() {
        songSelectionPanel = new JPanel();
        List<String> songContents = sds.getAudioContents();
        DefaultListModel defaultSongModel = new DefaultListModel();
        for (String name : songContents) {
            defaultSongModel.addElement(name);
        }
        JScrollPane redScroller = new JScrollPane();
        songList = new JList(defaultSongModel);
        redScroller.setViewportView(songList);
        redScroller.setBorder(new LineBorder(Color.RED,5));
        songSelectionPanel.add(redScroller);
        songSelectionPanel.add(Box.createHorizontalGlue());
        JScrollPane greenScroller = new JScrollPane();
        addedSongList = new JList(new DefaultListModel());
        greenScroller.setViewportView(addedSongList);
        greenScroller.setBorder(new LineBorder(Color.GREEN,5));
        songSelectionPanel.add(greenScroller);
    }

    // REQUIRES: valid image file paths
    // MODIFIES: this
    // EFFECTS: adds
    public void addImageElements() throws IOException {
        imagePanel = new JPanel();
        List<String> imageContents = getPlaylistCoverContents();
        DefaultListModel imageModel = new DefaultListModel();
        for (String file : imageContents) {
            imageModel.addElement(file);
        }
        allCovers = new JList(imageModel);
        JScrollPane redScroller = new JScrollPane();
        redScroller.setViewportView(allCovers);
        redScroller.setBorder(new LineBorder(Color.RED,5));
        selectedCoverImage = new JLabel(new ImageIcon(ImageIO.read(new File(
                "./data/playlist_covers/default_cover.png"))));
        selectedCoverImage.setPreferredSize(new Dimension(PLAYLIST_COVER_SIZE,PLAYLIST_COVER_SIZE));
        resetToDefaultCover = new JButton("Reset to default playlist cover");
        imagePanel.add(redScroller);
        imagePanel.add(selectedCoverImage);
        imagePanel.add(resetToDefaultCover);
    }

    // MODIFIES: this
    // EFFECTS: defines all the instruction labels of this JFrame
    public void addInstructionLabels() {
        JLabel addDesc = new JLabel("Select/click on the songs below:");
        JLabel songAppear = new JLabel("Every added song will appear on the empty, green pane at the right end of "
                + "the screen!");
        JLabel songDelete = new JLabel("Select/click entries on the rightmost, green pane "
                + "to delete already added songs!");
        creatorBox.add(addDesc);
        creatorBox.add(songAppear);
        creatorBox.add(songDelete);
        addDesc.setAlignmentX(Component.CENTER_ALIGNMENT);
        songAppear.setAlignmentX(Component.CENTER_ALIGNMENT);
        songDelete.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    // MODIFIES: this
    // EFFECTS: adds a bunch of components to the creatorBox Box layout component
    public void addElements() {
        creatorBox = Box.createVerticalBox();
        creatorBox.add(namePanel);
        JLabel bigSongSelectionLabel = new JLabel("SONG SELECTION");
        bigSongSelectionLabel.setFont(new Font("Impact",Font.BOLD,30));
        bigSongSelectionLabel.setForeground(Color.RED);
        creatorBox.add(bigSongSelectionLabel);
        bigSongSelectionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        addInstructionLabels();
        creatorBox.add(songSelectionPanel);
        JLabel bigCoverSelectionLabel = new JLabel("PLAYLIST COVER SELECTION");
        bigCoverSelectionLabel.setFont(new Font("Impact",Font.BOLD,30));
        bigCoverSelectionLabel.setForeground(Color.RED);
        creatorBox.add(bigCoverSelectionLabel);
        bigCoverSelectionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel chooseCover = new JLabel("Select/click on the covers below:");
        creatorBox.add(chooseCover);
        chooseCover.setAlignmentX(Component.CENTER_ALIGNMENT);
        creatorBox.add(imagePanel);
        addPlaylist = new JButton("Add/Save playlist");
        creatorBox.add(addPlaylist);
        addPlaylist.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(creatorBox);
    }

    // EFFECTS: adds all the general listeners for this JFrame
    public void addListeners() {
        handleListListeners();
        handleButtonListeners();
    }

    // MODIFIES: this
    // EFFECTS: handles all the list listeners active on this JFrame
    public void handleListListeners() {
        songList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                addToPlaylist();
            }
        });
        addedSongList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                removeFromPlaylist();
            }
        });
        allCovers.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                try {
                    updateCoverPreview();
                } catch (IOException ex) {
                    return;
                }
            }
        });

    }

    // MODIFIES: this
    // EFFECTS: handles all the button listeners active on this JFrame
    public void handleButtonListeners() {
        resetToDefaultCover.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    resetToDefaultPlaylistCover();
                } catch (IOException ex) {
                    return;
                }
            }
        });
        addPlaylist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    addAndSavePlaylist();
                } catch (IOException ex) {
                    return;
                }
            }

        });
    }

    // MODIFIES: this
    // EFFECTS: disposes the current instance of this JFrame from memory
    public void disposeWindow() {
        this.dispose();
    }

    // REQUIRES: valid file paths
    // MODIFIES: this
    // EFFECTS: saves the currently built playlist to a file
    public void addAndSavePlaylist() throws IOException {
        PlaylistCreatorScreen ref = this;
        DefaultListModel model = (DefaultListModel) addedSongList.getModel();
        if (model.size() == 0) {
            JOptionPane.showMessageDialog(ref,"A playlist needs at least one song to be created!",
                    "Empty playlist warning",
                    JOptionPane.WARNING_MESSAGE);

        } else {
            ArrayList<Song> songs = new ArrayList<Song>();
            String playlistName = nameField.getText();
            int songIndex = 0;
            int timeStamp = 0;
            for (int i = 0; i < model.size(); i++) {
                String path = "./data/audio/" + model.getElementAt(i);
                Song song = new Song(path);
                songs.add(song);
            }
            Playlist pl = new Playlist(playlistName,songs);
            writer = new PlaylistWriter(pl,songIndex,timeStamp,selectedCoverPath);
            writer.writeToFile();
            reference.updatePlaylists();
            MusicPlayerEvents.createPlaylistLog(pl);
            disposeWindow();
        }


    }

    // MODIFIES: this
    // EFFECTS: resets the selected cover image to the default playlist cover icon
    public void resetToDefaultPlaylistCover() throws IOException {
        Image coverImg = ImageIO.read(new File(DEFAULT_COVER_PATH)).getScaledInstance(selectedCoverImage.getWidth(),
                selectedCoverImage.getHeight(),Image.SCALE_SMOOTH);
        selectedCoverImage.setIcon(new ImageIcon(coverImg));
    }

    // MODIFIES: this
    // EFFECTS: adds the selected song to the added songs list (transfers it over)
    public void addToPlaylist() {
        DefaultListModel model = (DefaultListModel) songList.getModel();
        int selectedIndex = songList.getSelectedIndex();
        if (selectedIndex != -1) {
            String songReference = (String) model.get(selectedIndex);
            songList.setSelectedIndex(-1);
            DefaultListModel addedModel = (DefaultListModel) addedSongList.getModel();
            if (!addedModel.contains(songReference)) {
                String curPlaylistName = nameField.getText();
                addedModel.addElement(songReference);
                MusicPlayerEvents.addSongLog(curPlaylistName,
                        new Song("data/audio/" + songReference));

            }

        }

    }

    // MODIFIES: this
    // EFFECTS: removes the selected item from the added song list, and updates the Jlist
    public void removeFromPlaylist() {
        DefaultListModel model = (DefaultListModel) addedSongList.getModel();
        int selectedIndex = addedSongList.getSelectedIndex();
        if (selectedIndex != -1) {
            String songReference = (String) model.getElementAt(selectedIndex);
            String curPlaylistName = nameField.getText();
            addedSongList.setSelectedIndex(-1);
            model.remove(selectedIndex);
            MusicPlayerEvents.removeSongLog(curPlaylistName,
                    new Song("data/audio/" + songReference));

        }
    }


    // MODIFIES: this
    // EFFECTS: updates the cover preivew, if the user swaps the selected JList item for image covers
    public void updateCoverPreview() throws IOException {
        int selectedIndex = allCovers.getSelectedIndex();
        DefaultListModel coversModel = (DefaultListModel) allCovers.getModel();
        String coverImageFileName = (String) coversModel.get(selectedIndex);
        if (selectedIndex != -1) {
            Image coverImg = ImageIO.read(new File("./data/playlist_covers/user_added/"
                    + coverImageFileName)).getScaledInstance(selectedCoverImage.getWidth(),
                    selectedCoverImage.getHeight(),Image.SCALE_SMOOTH);
            selectedCoverImage.setIcon(new ImageIcon(coverImg));
            selectedCoverPath = "./data/playlist_covers/user_added/" + coverImageFileName;
            allCovers.setSelectedIndex(-1);
        }

    }

    // EFFECTS: returns a list of strings, with files names from "data/playlist_covers/user_added"
    public List<String> getPlaylistCoverContents() {
        List<String> contents = new ArrayList<String>();
        File playlistCoverFolder = new File("data/playlist_covers/user_added");

        for (File f : playlistCoverFolder.listFiles()) {
            contents.add(f.getName());
        }

        return contents;
    }


}
