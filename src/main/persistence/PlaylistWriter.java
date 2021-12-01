package persistence;

import model.Playlist;
import model.Song;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


/*
 * A class that is responsible for writing/saving user playlist data to memory via JSON files
 */
public class PlaylistWriter {
    private Playlist playlist;
    private String sourceFile;
    private FileWriter writer;
    private int songIndex;
    private int currentTimeInSeconds;
    private String playlistCoverImagePath;

    // EFFECTS: creates a constructor with the playlist, song index and current time stamp to write to file;
    //                                          throws IOException if the file is non existent or corrupted
    public PlaylistWriter(Playlist playlist,int songIndex,int currentTimeStamp,String coverPath) throws IOException {
        this.playlist = playlist;
        this.currentTimeInSeconds = currentTimeStamp;
        this.songIndex = songIndex;
        playlistCoverImagePath = coverPath;
        sourceFile = "data/playlist_data/" + playlist.getPlaylistName() + ".json";
        writer = new FileWriter(new File(sourceFile),false);


    }

    // MODIFIES: this
    // EFFECTS: overwrites the playlist json file, with the playlist object attributes;
    //                                                        throws IOException if the file doesn't exist or corrupt
    public void writeToFile() throws IOException {
        JSONArray arr = new JSONArray();
        JSONObject data = new JSONObject();
        ArrayList<Song> songs = playlist.getSongs();
        data.put("name",playlist.getPlaylistName());
        data.put("songs",songs);
        data.put("song_index",songIndex);
        data.put("time_stamp", currentTimeInSeconds);
        data.put("cover_path",playlistCoverImagePath);
        arr.put(0,data);
        writer.write(arr.toString());
        closeWriter();
    }

    // EFFECTS: closes the writer; throws IOException if the file doesn't exist or is corrupted
    private void closeWriter() throws IOException {
        writer.close();
    }


}
