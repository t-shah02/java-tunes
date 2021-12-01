package model;

public class MusicPlayerEvents {

    private static EventLog logInstance = EventLog.getInstance();

    // MODIFIES: this
    // EFFECTS: helper method for loading playlists
    public static void loadPlaylistLog(Playlist p) {
        Event e = new Event("Loaded " + p.getPlaylistName() + " from: " + "data/playlist_data/"
                + p.getPlaylistName() + ".json");
        logInstance.logEvent(e);
    }

    // MODIFIES: this
    // EFFECTS: helper method for saving playlists
    public static void savePlaylistLog(Playlist p) {
        Event e = new Event("Saved " + p.getPlaylistName() + " to: " + "data/playlist_data/"
                + p.getPlaylistName() + ".json");
        logInstance.logEvent(e);
    }

    // MODIFIES: this
    // EFFECTS: helper method for logging a song (toAdd) to target
    public static void addSongLog(Playlist target, Song toAdd) {
        Event e = new Event("Added song: " + toAdd.getSongPath() + " playlist: " + target.getPlaylistName());
        logInstance.logEvent(e);
    }

    // MODIFIES: this
    // EFFECTS: helper method for logging a song to an incomplete playlist (when its added)
    public static void addSongLog(String target, Song toAdd) {
        Event e = new Event("Added song: " + toAdd.getSongPath() + " to incomplete playlist: " + target);
        logInstance.logEvent(e);
    }

    // MODIFIES: this
    // EFFECTS: helper method for logging if a song is removed from target
    public static void removeSongLog(Playlist target, Song toAdd) {
        Event e = new Event("Removed song: " + toAdd.getSongPath() + " playlist: " + target.getPlaylistName());
        logInstance.logEvent(e);
    }

    // MODIFIES: this
    // EFFECTS: helper for logging a removed song to an incomplete playlist (when its removed)
    public static void removeSongLog(String target, Song toAdd) {
        Event e = new Event("Removed song: " + toAdd.getSongPath() + " incomplete playlist: " + target);
        logInstance.logEvent(e);
    }

    // MODIFIES: this
    // EFFECTS: helper for logging when a single track window is opened
    public static void openSingleSongLog(Song s) {
        Event e = new Event("Opened song: " + s.getSongPath());
        logInstance.logEvent(e);
    }

    // MODIFIES: this
    // EFFECTS: helper for logging when a new playlist window is opened
    public static void openPlaylistLog(Playlist p) {
        Event e = new Event("Opened playlist: " + p.getPlaylistName());
        logInstance.logEvent(e);
    }

    // MODIFIES: this
    // EFFECTS: helper for logging when a playlist is created
    public static void createPlaylistLog(Playlist p) {
        Event e = new Event("A playlist called: " + p.getPlaylistName() + " was created");
        logInstance.logEvent(e);
    }

    // MODIFIES: this
    // EFFECTS: helper for logging when a playlist is deleted
    public static void deletePlaylistLog(String name) {
        Event e = new Event("Deleted playlist: " + name + " from data/playlist_data");
        logInstance.logEvent(e);
    }

    // MODIFIES: this
    // EFFECTS: helper for logging when a playlist file is read and a song is added to that read playlist
    public static void loadSongFromPlaylistLog(Playlist p, Song s) {
        Event e = new Event("Added song: " + s.getSongPath() + " to loaded playlist: " + p.getPlaylistName());
        logInstance.logEvent(e);
    }
}
