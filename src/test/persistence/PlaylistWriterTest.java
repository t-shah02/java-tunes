package persistence;

import model.Playlist;
import static org.junit.jupiter.api.Assertions.*;

import model.Song;
import model.SongDataStorage;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.List;

public class PlaylistWriterTest {

    @Test
    void testInvalidFile() {
        try {
            Playlist garbage = new Playlist("|garbage|");
            PlaylistWriter writer = new PlaylistWriter(garbage,0,0,
                    "./data/playlist_covers/terraria_cover.png");
            fail("Exception should be caught");
        } catch (IOException e) {
            // expected to fail
        }

    }

    @Test
    void testValidFile() {
        try {
            Playlist valid = new Playlist("valid");
            SongDataStorage sds = new SongDataStorage();
            List<String> files = sds.getAudioContents();

            for (String file : files) {
                Song s = new Song("data/audio/" + file);
                valid.addSong(s);
            }

            PlaylistWriter pw = new PlaylistWriter(valid,0,0,
                    "./data/playlist_covers/terraria_cover.png");
            pw.writeToFile();
            List<String> dataContents = sds.getPlaylistDataContents();
            PlaylistLoader pl = new PlaylistLoader();
            assertEquals(pl.getSongIndexFromFile(valid),0);
            assertEquals(pl.getTimeStampFromFile(valid),0);
            ArrayList<Playlist> playlists = pl.loadAllPlaylists();
            assertEquals(playlists.size(),dataContents.size());
            int expectedSize = playlists.get(0).getSongs().size();
            assertEquals(playlists.get(0).getSongs().size(),expectedSize);


        } catch (IOException e) {
            fail("The playlist name yields a valid file, this exception should not be caught!");
        }
    }

}
