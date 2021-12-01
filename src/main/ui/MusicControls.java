package ui;

import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/*
 * Contains all the music control features that the user can manipulate on a media player for their music within the UI
 */
public class MusicControls {

    // REQUIRES: player is: 1. is defined, with a valid media source 2. it isn't playing anything currently
    // EFFECTS: starts playback of the player's media source attribute
    public void play(MediaPlayer player) {
        player.play();
    }

    // REQUIRES: player is: 1. is defined, with a valid media source 2. it is playing a song currently
    // EFFECTS: stops the current playback of player's media source attribute
    public void stop(MediaPlayer player) {
        Thread musicStop = new Thread(new Runnable() {
            @Override
            public void run() {
                player.stop();
            }
        });
        musicStop.start();
    }


    // REQUIRES: player is: 1. is defined, with a valid media source 2. it is playing a song currently | (valid bounds)
    // EFFECTS: seeks the current playback of the player's media, to the specified jslider value
    public void seek(MediaPlayer player, int seconds) {
        Thread seekSong = new Thread(new Runnable() {
            @Override
            public void run() {
                player.seek(Duration.seconds(seconds));
            }
        });
        seekSong.start();
    }


    // REQUIRES: player is: 1. is defined, with a valid media source 2. it is playing a song currently
    // EFFECTS: restarts the passed in media player instance playback.
    public void restart(MediaPlayer player) {
        Thread restartSong = new Thread(new Runnable() {
            @Override
            public void run() {
                player.seek(Duration.seconds(0));
            }
        });
        restartSong.start();
    }

    // REQUIRES: player is: 1. is defined, with a valid media source 2. it is playing a song currently
    // EFFECTS: pauses the playback from the passed in media player instance.
    public void pause(MediaPlayer player) {
        Thread pauseSong = new Thread(new Runnable() {
            @Override
            public void run() {
                player.pause();
            }
        });
        pauseSong.start();
    }


    // REQUIRES: player is: 1. is defined, with a valid media source 2. it is playing a song currently
    // EFFECTS: returns a string, which is formatted in timestamp notation, given the current song time in seconds.
    public String timeStampOfCurrentTime(MediaPlayer player) {
        int currentTime = (int) player.getCurrentTime().toSeconds();
        int minutes = currentTime / 60;
        int seconds = currentTime - (minutes * 60);

        if (seconds < 10) {
            return Integer.toString(minutes) + ":0" + Integer.toString(seconds);
        }

        return Integer.toString(minutes) + ":" + Integer.toString(seconds);
    }










}
