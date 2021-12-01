package persistence;


import model.Playlist;
import model.Song;
import model.SongDataStorage;
import org.json.JSONArray;
import org.json.JSONObject;



import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/*
 * A class that is responsible for loading playlist data out of stored json files
 */
public class PlaylistLoader {

    // EFFECTS: builds a list of playlists from the json files in the data/playlist_data directory;
    //                                              throws IOException for invalid files or files that don't exist
    public ArrayList<Playlist> loadAllPlaylists() throws IOException {
        ArrayList<Playlist> playlists = new ArrayList<Playlist>();
        SongDataStorage sds = new SongDataStorage();
        List<String> dataFiles = sds.getPlaylistDataContents();

        // loop through all files within playlist_data directory
        for (String dataFile : dataFiles) {
            String fileData = readFile("data/playlist_data/" + dataFile);
            JSONArray arr = new JSONArray(fileData);
            JSONObject obj = (JSONObject) arr.get(0);
            String name = (String) obj.get("name"); // pull name
            JSONArray jsonSongs = (JSONArray) obj.get("songs"); // pull Song Array
            ArrayList<Song> songs = new ArrayList<Song>();

            for (int i = 0; i < jsonSongs.length(); i++) {
                JSONObject songData = new JSONObject(jsonSongs.get(i).toString());
                String songPath = songData.get("songPath").toString();
                songs.add(new Song(songPath));
            }
            Playlist p = new Playlist(name,songs);
            playlists.add(p);

        }
        return playlists;
    }

    // EFFECTS: pulls the last song index of the song played in the given playlist; throws IOException for invalid file
    public int getSongIndexFromFile(Playlist p) throws IOException {

        String fileData = readFile("data/playlist_data/" + p.getPlaylistName() + ".json");
        JSONArray arr = new JSONArray(fileData);
        JSONObject obj = (JSONObject) arr.get(0);
        int songIndex = Integer.parseInt(obj.get("song_index").toString());
        return songIndex;

    }

    // EFFECTS: pulls the last timestamp of the song played in the given playlist; throws IOException for invalid file
    public int getTimeStampFromFile(Playlist p) throws IOException {
        String fileData = readFile("data/playlist_data/" + p.getPlaylistName() + ".json");
        JSONArray arr = new JSONArray(fileData);
        JSONObject obj = (JSONObject) arr.get(0);
        int timeStamp = (Integer) obj.get("time_stamp");
        return timeStamp;
    }

    // EFFECTS: pulls the playlist cover path for the given playlist; throws IOException for invalid file
    public String getCoverPathFromFile(Playlist p) throws IOException {
        String fileData = readFile("data/playlist_data/" + p.getPlaylistName() + ".json");
        JSONArray arr = new JSONArray(fileData);
        JSONObject obj = (JSONObject) arr.get(0);
        String coverPath = obj.get("cover_path").toString();
        return coverPath;
    }



    // This method references code from the JSONSerializationDemo repo from CPSC 210
    // Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/main/persistence/JsonReader.java
    // EFFECTS: reads source file as string and returns it; throws IOException if the file doesn't exist
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        } catch (IOException e) {
            return "";
        }

        return contentBuilder.toString();
    }

}
