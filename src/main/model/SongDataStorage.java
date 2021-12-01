package model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/*
 * Responsible for all the file input/output methods within the application (mainly for reading in audio contents)
 */
public class SongDataStorage {

    // EFFECTS: returns a list of strings, containing the names of the files present in the "data/audio" directory
    public List<String> getAudioContents() {
        List<String> contents = new ArrayList<String>();
        File audioFolder = new File("data/audio");

        for (File f : audioFolder.listFiles()) {
            contents.add(f.getName());
        }

        return contents;
    }

    // EFFECTS: returns a list of strings, containing the names of the files present in "data/playlist_data"
    public List<String> getPlaylistDataContents() {
        List<String> contents = new ArrayList<String>();
        File playlistDataFolder = new File("data/playlist_data");

        for (File f : playlistDataFolder.listFiles()) {
            contents.add(f.getName());
        }

        return contents;
    }



    public int getNumberOfAudioContents(List<String> contents) {
        return contents.size();
    }

}
