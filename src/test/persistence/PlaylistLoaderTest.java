package persistence;
import static org.junit.jupiter.api.Assertions.*;

import model.Playlist;
import model.SongDataStorage;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.List;


public class PlaylistLoaderTest {
    private PlaylistLoader pl;

    @BeforeEach
    void runBefore() {
        pl = new PlaylistLoader();
    }

    @Test
    void testloadAllPlaylists() {
        try {
            SongDataStorage sds = new SongDataStorage();
            List<String> dataContents = sds.getPlaylistDataContents();
            assertEquals(pl.loadAllPlaylists().size(),dataContents.size());
            assertEquals(pl.loadAllPlaylists().get(0).getPlaylistName(),"eve");
            int expectedSize = pl.loadAllPlaylists().get(0).getSongs().size();
            assertEquals(pl.loadAllPlaylists().get(0).getSongs().size(),expectedSize);
            int songSize = pl.loadAllPlaylists().get(0).getSongs().size();
            int lastIndex = songSize - 1;
            assertEquals(pl.loadAllPlaylists().get(0).getSongs().get(lastIndex).getNickName(),null);

        } catch (IOException e) {
            fail("Couldn't load files in data/playlist_data");
        }
    }

    @Test
    void testInvalidPlaylist() {
        try {
            Playlist test = new Playlist("null");
            pl.getSongIndexFromFile(test);
            fail("Error should be caught for file that does not exist");
        } catch (JSONException | IOException e) {
            // expected
        }

        try {
            Playlist testOne = new Playlist("null");
            pl.getTimeStampFromFile(testOne);
            fail("Error should be caught for file that does not exist");
        } catch (IOException | JSONException e) {
            // expected
        }
    }

    @Test
    void testGetSongIndexFromFile() {
        try {
            Playlist tester = new Playlist("testing-playlist");
            assertEquals(pl.getSongIndexFromFile(tester),0);
        } catch (IOException e) {
            fail("testing-playlist.json is a real file, hence this should not fail");
        }
    }

    @Test
    void testGetTimeStampFromFile() {
        try {
            Playlist tester = new Playlist("testing-playlist");
            int expected = pl.getTimeStampFromFile(tester);
            assertEquals(pl.getTimeStampFromFile(tester),expected);
        } catch (IOException e) {
            fail("testing-playlist.json is a real file, hence this should not fail");
        }
    }

    @Test
    void testGetCoverPathFromFile() {
        try {
            Playlist tester = new Playlist("testing-playlist");
            assertEquals(pl.getCoverPathFromFile(tester),"data/playlist_covers/default_cover.png");
        } catch (IOException e) {
            fail("testing-playlist.json is a real file, hence this should not fail");
        }
    }










}

