package model;

import javafx.scene.media.Media;
import java.io.File;

/*
 * Represents information about a song's sound file.
 */

public class Song {
    private Media source;
    private String nickName;
    private String songPath;

    // Constructs a playlist type song
    // REQUIRES: songPath must be a valid file path that leads to an audio related source
    // MODIFIES: this
    public Song(String nickName, String songPath) {
        this.nickName = nickName;
        this.songPath = songPath;
    }

    // Constructs a normal song, for playback outside of a created playlist
    // REQUIRES: songPath must be a valid file path that leads to an audio related source
    // MODIFIES: this
    public Song(String songPath) {
        this.songPath = songPath;
    }


    // getters for the Song class are included below:

    public String getNickName() {
        return nickName;
    }

    public String getSongPath() {
        return songPath;
    }













}
