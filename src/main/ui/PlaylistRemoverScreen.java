package ui;

import model.MusicPlayerEvents;
import model.Playlist;

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

/*
 * Represents the playlist remover screen JFrame
 */
public class PlaylistRemoverScreen extends JFrame {

    private static final int WINDOW_WIDTH = 450;
    private static final int WINDOW_HEIGHT = 400;


    private HomeScreen parent;
    private ArrayList<Playlist> playlists;

    private Box removerBox;
    private JList allPlaylists;
    private JList selectedToRemove;
    private JLabel descOne;
    private JLabel descTwo;
    private JButton finalRemove;

    // MODIFIES: this
    // EFFECTS: creates a new playlist remover screen
    public PlaylistRemoverScreen(HomeScreen hs, ArrayList<Playlist> playlists) {
        parent = hs;
        this.playlists = playlists;
        completeFrame();
    }

    // EFFECTS: completes the entire JFrame
    public void completeFrame() {
        makeElements();
        addElements();
        addListeners();
        initializeFrame();
    }

    // MODIFIES: this
    // EFFECTS: creates all the gui components for this screen
    public void makeElements() {
        removerBox = Box.createVerticalBox();
        DefaultListModel model = new DefaultListModel();
        for (Playlist p : playlists) {
            model.addElement(p.getPlaylistName());
        }
        descOne = new JLabel("Click/Select below to choose the playlists that you want to remove:");
        descTwo = new JLabel("Playlists selected to remove (click/select a playlist to cancel its removal):");
        allPlaylists = new JList(model);
        allPlaylists.setSelectedIndex(-1);
        selectedToRemove = new JList(new DefaultListModel());
        finalRemove = new JButton("Remove the selected playlists");
    }

    // MODIFIES: this
    // EFFECTS: adds all the gui components to the removerBox layout component
    public void addElements() {
        removerBox.add(descOne);
        descOne.setAlignmentX(Component.CENTER_ALIGNMENT);
        JScrollPane redScroller = new JScrollPane();
        redScroller.setBorder(new LineBorder(Color.RED, 5));
        redScroller.setViewportView(allPlaylists);
        removerBox.add(redScroller);
        redScroller.setAlignmentX(Component.CENTER_ALIGNMENT);
        removerBox.add(descTwo);
        descTwo.setAlignmentX(Component.CENTER_ALIGNMENT);
        JScrollPane greenScroller = new JScrollPane();
        greenScroller.setBorder(new LineBorder(Color.GREEN, 5));
        greenScroller.setViewportView(selectedToRemove);
        removerBox.add(greenScroller);
        greenScroller.setAlignmentX(Component.CENTER_ALIGNMENT);
        removerBox.add(finalRemove);
        finalRemove.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(removerBox);
    }

    // MODIFIES: this
    // EFFECTS: creates the intiial attributes for this JFrame
    public void initializeFrame() {
        this.setResizable(false);
        this.setTitle("Playlist remover");
        this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

    // EFFECTS: adds all the listeners for this JFrame
    public void addListeners() {
        addListListeners();
        addButtonListeners();
    }

    // MODIFIES: this
    // EFFECTS: adds all the list listeners for this JFrame
    public void addListListeners() {
        allPlaylists.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                addToRemoveList();
            }

        });
        selectedToRemove.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                removeFromRemoveList();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: adds all the button listeners for this JFrame
    public void addButtonListeners() {
        PlaylistRemoverScreen reference = this;
        finalRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultListModel selectionModel = (DefaultListModel) selectedToRemove.getModel();
                if (selectionModel.size() == 0) {
                    JOptionPane.showMessageDialog(reference,"Please select at least one playlist"
                            + " to remove","Empty selection error",JOptionPane.WARNING_MESSAGE);
                } else {
                    deleteSelectedFromSystem(selectionModel);
                    try {
                        parent.updatePlaylists();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    disposeWindow();
                }
            }
        });
    }

    // EFFECTS: deletes the selected playlist data files from the user's system
    public void deleteSelectedFromSystem(DefaultListModel selectionModel) {
        for (int i = 0; i < selectionModel.size(); i++) {
            String playlistDataFilePath = "./data/playlist_data/"
                    + (String) selectionModel.getElementAt(i) + ".json";
            String playlistDataFileName = (String) selectionModel.getElementAt(i) + ".json";
            MusicPlayerEvents.deletePlaylistLog(playlistDataFileName);
            File file = new File(playlistDataFilePath);
            file.delete();
        }
    }

    // EFFECTS: disposes of the current instance of this JFrame from memory
    public void disposeWindow() {
        this.dispose();
    }

    // MODIFIES: this
    // EFFECTS: adds the selected playlist into the selectedToRemove Jlist
    public void addToRemoveList() {
        DefaultListModel model = (DefaultListModel) allPlaylists.getModel();
        int selectedIndex = allPlaylists.getSelectedIndex();
        if (selectedIndex != -1) {
            String playlistReference = (String) model.getElementAt(selectedIndex);
            DefaultListModel selectedModel = (DefaultListModel) selectedToRemove.getModel();
            if (!selectedModel.contains(playlistReference)) {
                selectedModel.addElement(playlistReference);
                allPlaylists.setSelectedIndex(-1);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: removes the selected added playlist for removal
    public void removeFromRemoveList() {
        DefaultListModel model = (DefaultListModel) selectedToRemove.getModel();
        int selectedIndex = selectedToRemove.getSelectedIndex();
        if (selectedIndex != -1) {
            model.remove(selectedIndex);
        }
    }
}
