package model;
import static org.junit.jupiter.api.Assertions.*;

import javafx.scene.media.Media;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SongTest {
    private Song test;

    @BeforeEach
    void runBefore() {
        test = new Song("lol","data/audio/crybaby.mp3");
    }

    @Test
    void testGetNickName() {
        assertEquals(test.getNickName(),"lol");
    }

    @Test
    void testGetSongPath() {
        assertEquals(test.getSongPath(),"data/audio/crybaby.mp3");
        Song test2 = new Song("data/audio/Terraria Music - Boss 1.mp3");
        assertEquals(test2.getSongPath(),"data/audio/Terraria Music - Boss 1.mp3");
    }



}
