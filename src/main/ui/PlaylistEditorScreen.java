package ui;

import model.MusicPlayerEvents;
import model.Playlist;
import model.Song;
import model.SongDataStorage;
import persistence.PlaylistLoader;
import persistence.PlaylistWriter;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * Represents the playlist editor screen JFrame
 */
public class PlaylistEditorScreen extends JFrame {
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 500;

    private HomeScreen parent;
    private ArrayList<Playlist> playlists;
    private SongDataStorage sds;
    private PlaylistLoader loader;
    private PlaylistWriter writer;

    private Box editorBox;
    private JList allPlaylists;
    private JList allSongInsidePlaylist;
    private JList allDirectorySongs;
    private JList songsToRemove;
    private JLabel descOne;
    private JButton savePlaylist;

    // MODIFIES: this
    // EFFECTS: creates a new PlaylistEditorScreen instance (frame)
    public PlaylistEditorScreen(HomeScreen hs, ArrayList<Playlist> playlists) {
        parent = hs;
        this.playlists = playlists;
        sds = new SongDataStorage();
        loader = new PlaylistLoader();
        completeFrame();
    }

    // EFFECTS: completes the frame to display to the user
    public void completeFrame() {
        makeElements();
        addElements();
        addListeners();
        initializeFrame();
    }

    // MODIFIES: this
    // EFFECTS: creates all the basic gui element for the editor box layout components
    public void makeElements() {
        editorBox = Box.createVerticalBox();
        DefaultListModel playlistsModel = new DefaultListModel();
        DefaultListModel songsModel = new DefaultListModel();
        for (Playlist p : playlists) {
            playlistsModel.addElement(p.getPlaylistName());
        }
        List<String> songContents = sds.getAudioContents();
        for (String file : songContents) {
            songsModel.addElement(file);
        }
        descOne = new JLabel("Click/Select below to choose the playlists that you want to edit:");
        descOne.setFont(new Font("Impact",Font.PLAIN,20));
        allPlaylists = new JList(playlistsModel);
        allPlaylists.setSelectedIndex(-1);
        allDirectorySongs = new JList(songsModel);
        allDirectorySongs.setSelectedIndex(-1);
        allSongInsidePlaylist = new JList(new DefaultListModel());
        songsToRemove = new JList(new DefaultListModel());
        savePlaylist = new JButton("Save the selected playlist");
    }

    // MODIFIES: this
    // EFFECTS: adds all the components to the editorBox layout component
    public void addElements() {
        editorBox.add(descOne);
        descOne.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPanel boxArray = new JPanel();
        boxArray.setLayout(new FlowLayout());
        Box directorySongsBox = getDirectorySongsBox();
        Box playlistsBox = getPlaylistsBox();
        Box songsInPlaylistBox = getSelectedPlaylistSongBox();
        boxArray.add(directorySongsBox);
        boxArray.add(playlistsBox);
        boxArray.add(songsInPlaylistBox);
        editorBox.add(boxArray);
        Box removalBox = getRemovalBox();
        editorBox.add(removalBox);
        editorBox.add(savePlaylist);
        savePlaylist.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(editorBox);
    }

    // EFFECTS: returns the removed songs box layout component
    public Box getRemovalBox() {
        Box removalBox = Box.createVerticalBox();
        JLabel removeDesc = new JLabel("Songs that will be removed from the selected playlist:");
        removalBox.add(removeDesc);
        removeDesc.setAlignmentX(Component.CENTER_ALIGNMENT);
        JScrollPane redScroller = new JScrollPane();
        redScroller.setBorder(new LineBorder(Color.RED,5));
        redScroller.setViewportView(songsToRemove);
        removalBox.add(redScroller);
        return removalBox;
    }

    // EFFECTS: returns the directory songs box layout component
    public Box getDirectorySongsBox() {
        Box songBox = Box.createVerticalBox();
        JLabel songDesc = new JLabel("Songs found in the data/audio directory:");
        JLabel instructions = new JLabel("Click on a song to add it to the selected playlist");
        songBox.add(songDesc);
        songBox.add(instructions);
        songDesc.setAlignmentX(Component.CENTER_ALIGNMENT);
        instructions.setAlignmentX(Component.CENTER_ALIGNMENT);
        JScrollPane blueScroller = new JScrollPane();
        blueScroller.setBorder(new LineBorder(Color.BLUE,5));
        blueScroller.setViewportView(allDirectorySongs);
        songBox.add(blueScroller);
        return songBox;
    }

    // EFFECTS: returns the playlist list box layout component
    public Box getPlaylistsBox() {
        Box playlistsBox = Box.createVerticalBox();
        JLabel playlistDesc = new JLabel("Playlists found in the data/playlist_data directory");
        JLabel instructions = new JLabel("Click on a playlist to see the song contents inside of it");
        playlistsBox.add(playlistDesc);
        playlistsBox.add(instructions);
        playlistDesc.setAlignmentX(Component.CENTER_ALIGNMENT);
        instructions.setAlignmentX(Component.CENTER_ALIGNMENT);
        JScrollPane blueScroller = new JScrollPane();
        blueScroller.setBorder(new LineBorder(Color.BLUE,5));
        blueScroller.setViewportView(allPlaylists);
        playlistsBox.add(blueScroller);
        return playlistsBox;
    }

    // EFFECTS: returns the songs inside selected playlist box layout component
    public Box getSelectedPlaylistSongBox() {
        Box selectedSongBox = Box.createVerticalBox();
        JLabel allSongsDesc = new JLabel("Songs inside this playlist:");
        JLabel instructions = new JLabel("Click on a song to remove it from this selected playlist");
        selectedSongBox.add(allSongsDesc);
        selectedSongBox.add(instructions);
        allSongsDesc.setAlignmentX(Component.CENTER_ALIGNMENT);
        instructions.setAlignmentX(Component.CENTER_ALIGNMENT);
        JScrollPane greenScroller = new JScrollPane();
        greenScroller.setBorder(new LineBorder(Color.GREEN,5));
        greenScroller.setViewportView(allSongInsidePlaylist);
        selectedSongBox.add(greenScroller);
        return selectedSongBox;
    }


    // MODIFIES: this
    // EFFECTS: creates the initial attributes of this frame
    public void initializeFrame() {
        this.setResizable(false);
        this.setTitle("Playlist editor");
        this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

    // EFFECTS: adds all the listeners to this JFrame
    public void addListeners() {
        addListListeners();
        addButtonListeners();
    }

    // MODIFIES: this
    // EFFECTS: adds all the button listeners to this frame
    public void addButtonListeners() {
        PlaylistEditorScreen ref = this;
        savePlaylist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int playListIndex = allPlaylists.getSelectedIndex();
                if (playListIndex != -1) {
                    updatePlaylistFile(playListIndex);
                } else {
                    JOptionPane.showMessageDialog(ref,"No playlist selected","Playlist selection error",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    // EFFECTS: handles the actions of the save playlist button
    public void updatePlaylistFile(int playlistIndex) {
        PlaylistEditorScreen reference = this;
        String selectedPlaylistName = playlists.get(playlistIndex).getPlaylistName();
        try {

            DefaultListModel currentSongsModel = (DefaultListModel) allSongInsidePlaylist.getModel();
            if (currentSongsModel.size() == 0) {
                handleEmptyPlaylist(reference,selectedPlaylistName);
            } else {
                overWritePlaylist(selectedPlaylistName);
                JOptionPane.showMessageDialog(reference,"Successfully saved: " + selectedPlaylistName,
                        "Saved playlist message",JOptionPane.INFORMATION_MESSAGE);
                MusicPlayerEvents.savePlaylistLog(playlists.get(playlistIndex));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // REQUIRES: this
    // MODIFIES: this
    // EFFECTS: overwrites the selected playlist data with the newly, edited changes
    public void overWritePlaylist(String playlistName) throws IOException {
        Playlist read = new Playlist(playlistName);
        int lastIndex = 0;
        int lastTimeStamp = loader.getTimeStampFromFile(read);
        String coverPath = loader.getCoverPathFromFile(read);
        ArrayList<Song> updatedSongs = new ArrayList<Song>();
        DefaultListModel songs = (DefaultListModel) allSongInsidePlaylist.getModel();
        DefaultListModel removeModel = (DefaultListModel) songsToRemove.getModel();
        for (int i = 0; i < songs.size(); i++) {
            String songPath = "./data/audio/" + songs.getElementAt(i);
            updatedSongs.add(new Song(songPath));
        }
        Playlist overWrite = new Playlist(playlistName,updatedSongs);
        writer = new PlaylistWriter(overWrite,lastIndex,lastTimeStamp,coverPath);
        writer.writeToFile();
        refreshPlaylists();
        parent.updatePlaylists();
        removeModel.removeAllElements();
    }

    // REQUIRES: valid file paths
    // MODIFIES: this
    // EFFECTS: if the user has selected all songs to be removed, prompt them to remove the playlist
    public void handleEmptyPlaylist(PlaylistEditorScreen reference,String selectedPlaylistName) throws IOException {
        String[] buttons = {"Remove: " + selectedPlaylistName,"Don't remove: " + selectedPlaylistName};
        int response = JOptionPane.showOptionDialog(
                reference,
                selectedPlaylistName + " is empty, would you like to remove this playlist?",
                "Playlist removal option",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                buttons,
                0);
        if (response == 0) {
            DefaultListModel removeModel = (DefaultListModel) songsToRemove.getModel();
            File file = new File("./data/playlist_data/" + selectedPlaylistName + ".json");
            file.delete();
            refreshPlaylists();
            removeModel.removeAllElements();

        }
    }

    // MODIFIES: this
    // EFFECTS: refreshes the allPlaylistlist Jlist and updates the parent screen playlists programatically
    public void refreshPlaylists() throws IOException {
        playlists = loader.loadAllPlaylists();
        DefaultListModel playlistsModel = (DefaultListModel) allPlaylists.getModel();
        playlistsModel.removeAllElements();
        for (Playlist p : playlists) {
            playlistsModel.addElement(p.getPlaylistName());
        }
        parent.updatePlaylists();

    }

    // EFFECTS: adds all the list listeners of this JFrame
    public void addListListeners() {
        displaySongsInSelectedPlaylist();
        addSongsToSelectedPlaylist();
        addSongsToRemove();
        undoRemovalOfSongs();
    }

    // EFFECTS: lists all the songs inside a playlist
    public void displaySongsInSelectedPlaylist() {
        allPlaylists.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedIndex = allPlaylists.getSelectedIndex();
                if (selectedIndex != -1) {
                    allDirectorySongs.setSelectedIndex(-1);
                    Playlist current = playlists.get(selectedIndex);
                    ArrayList<Song> songs = current.getSongs();
                    DefaultListModel selectedSongModel = (DefaultListModel) allSongInsidePlaylist.getModel();
                    selectedSongModel.removeAllElements();
                    for (Song s : songs) {
                        String[] pathSplit = s.getSongPath().split("/");
                        String name = pathSplit[pathSplit.length - 1];
                        selectedSongModel.addElement(name);
                    }
                    DefaultListModel removeModel = (DefaultListModel) songsToRemove.getModel();
                    removeModel.removeAllElements();
                }

            }
        });
    }

    // EFFECTS: add more songs to the selected playlist
    public void addSongsToSelectedPlaylist() {
        allDirectorySongs.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                DefaultListModel directoryModel = (DefaultListModel) allDirectorySongs.getModel();
                int directorySongIndex = allDirectorySongs.getSelectedIndex();
                int playlistIndex = allPlaylists.getSelectedIndex();
                if (directorySongIndex != -1 && playlistIndex != -1) {
                    DefaultListModel allSongsModel = (DefaultListModel) allSongInsidePlaylist.getModel();
                    DefaultListModel removeModel = (DefaultListModel) songsToRemove.getModel();
                    String songName = (String) directoryModel.get(directorySongIndex);
                    if (!allSongsModel.contains(songName) && !removeModel.contains(songName)) {
                        allSongsModel.addElement(songName);
                        MusicPlayerEvents.addSongLog(playlists.get(playlistIndex),
                                new Song("data/audio/" + songName));
                    }
                }
            }
        });
    }

    // EFFECTS: add songs from an existing playlist to the removal que
    public void addSongsToRemove() {
        allSongInsidePlaylist.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedIndex = allSongInsidePlaylist.getSelectedIndex();
                DefaultListModel songModel = (DefaultListModel) allSongInsidePlaylist.getModel();
                if (selectedIndex != -1) {
                    String songReference = (String) songModel.getElementAt(selectedIndex);
                    int playlistIndex = allPlaylists.getSelectedIndex();
                    songModel.removeElement(songReference);
                    DefaultListModel removeModel = (DefaultListModel) songsToRemove.getModel();
                    removeModel.addElement(songReference);
                    MusicPlayerEvents.removeSongLog(playlists.get(playlistIndex),
                            new Song("data/audio/" + songReference));
                    allSongInsidePlaylist.setSelectedIndex(-1);
                }
            }
        });
    }

    // EFFECTS: transfers the selected song from the removal list, to the original playlist
    public void undoRemovalOfSongs() {
        songsToRemove.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedIndex = songsToRemove.getSelectedIndex();
                DefaultListModel removeModel = (DefaultListModel) songsToRemove.getModel();
                if (selectedIndex != -1) {
                    String songReference = (String) removeModel.getElementAt(selectedIndex);
                    int playlistIndex = allPlaylists.getSelectedIndex();
                    removeModel.removeElement(songReference);
                    DefaultListModel songsModel = (DefaultListModel) allSongInsidePlaylist.getModel();
                    songsModel.addElement(songReference);
                    MusicPlayerEvents.addSongLog(playlists.get(playlistIndex),
                            new Song("data/audio/" + songReference));
                    songsToRemove.setSelectedIndex(-1);
                }
            }
        });
    }


}
