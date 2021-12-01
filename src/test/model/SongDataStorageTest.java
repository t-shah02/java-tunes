package model;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.List;

public class SongDataStorageTest {
    @Test
    void testGetAudioContents() {
        SongDataStorage as = new SongDataStorage();
        List<String> contents = as.getAudioContents();
        assertEquals(as.getAudioContents().get(0),contents.get(0));
    }

    @Test
    void testGetNumberOfAudioContents() {
        SongDataStorage as = new SongDataStorage();
        List<String> contents = as.getAudioContents();
        assertEquals(as.getNumberOfAudioContents(contents),contents.size());
        assertFalse(as.getNumberOfAudioContents(contents) == 1);
    }
}
