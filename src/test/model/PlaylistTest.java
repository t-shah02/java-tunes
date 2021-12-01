package model;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class PlaylistTest {
    private Playlist testPlaylist;

    @BeforeEach
    void runBefore() {
        testPlaylist = new Playlist("test playlist");
    }

    @Test
    void testGetPlaylistName() {
        assertEquals(testPlaylist.getPlaylistName(),"test playlist");
    }

    @Test
    void testAddSong() {
        Song s = new Song("song","data/audio/crybaby.mp3");
        assertTrue(testPlaylist.addSong(s));
    }

    @Test
    void testGetSongs() {
        Playlist test = new Playlist("testing");

        Song s = new Song("song","data/audio/test.mp3");
        Song s1 = new Song("song","data/audio/test2.mp3");
        Song s2 = new Song("song","data/audio/test3.mp3");
        test.addSong(s);
        test.addSong(s1);
        test.addSong(s2);

        ArrayList<Song> songs = new ArrayList<Song>();
        songs.add(s);
        songs.add(s1);
        songs.add(s2);


        assertTrue(test.getSongs().equals(songs));


    }




}
