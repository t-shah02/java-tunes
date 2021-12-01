package model;


import java.util.ArrayList;
import java.util.List;

/*
 * A class that represents a list of song objects, in sequential order.
 */
public class Playlist {
    private ArrayList<Song> songs;
    private String playlistName;

    // This constructor makes a new Playlist object, with an assigned name and an empty list of songs
    // MODIFIES: this
    public Playlist(String playlistName) {
        this.playlistName = playlistName;
        songs = new ArrayList<Song>();
    }

    public Playlist(String playlistName,ArrayList<Song> songs) {
        this.playlistName = playlistName;
        this.songs = songs;
    }

    // REQUIRES: s (Song object) is assumed to have a valid audio file path and corresponding Media instance.
    // MODIFIES: this
    // EFFECTS: appends a new song to the end of a playlist, and returns true, given no song entry errors
    public boolean addSong(Song s) {
        songs.add(s);
        return true;
    }

    // getters

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public String getPlaylistName() {
        return playlistName;
    }



}
