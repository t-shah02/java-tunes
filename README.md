## Music Player in Java -  CPSC 210 Personal Project
## By: Tanish Shah

**Proposal**

The purpose of this application is to serve as a simple, modern music player with basic musis control functions and playlist creation tools.
The target audience will be any user that wants to have a computer application, where it is easier to organize
their mp3 music files into playlists and also those that prefer to listen to music on a local service, rather than
a remote service, such as Spotify. 

This project is of interest to me because I really enjoy listening to music in my spare time, and occasioanlly while I
study or do other work. I am also just interested in making a project that is more geared towards audio, as I have never
programmed any other projects like that in the past. 

**User Stories**
- As a user, I can add multiple songs that I like to a newly, built playlist.
- As a user, I can swap from playing either a single song or a created playlist.
- As a user, I can seek through a song, to any timestamp I want, given that this timestamp is in bounds.
- As a user, I can manipulate basic music controls on a currently playing song, such as stop, pause, and restart.
- As a user, I can save the songs of my playlist in the order they were added, and save the last song, and its timestamp
    after I exited the playlist. 
- As a user, I can load my saved playlists, and once I load a specific playlist, I can pick off from last song and the 
     timestamp I left at. 
- As a user, I can edit saved playlists, and I can either add more songs to them or 
    remove already added songs, and save all those changes I made.
- As a user, I can open mp3 files from own system.
- As a user, I can add optional playlist covers, while I am building a playlist
    for the first time.

**Phase 4: Task 2**

**This is a sample history of logging events generated, during one trial of the application:**

Thu Nov 25 16:35:11 PST 2021
Added song: ./data/audio/Official髭男dism - Pretender［Official Video］.mp3 to loaded playlist: anime

Thu Nov 25 16:35:11 PST 2021
Added song: ./data/audio/RADWIMPS - 大丈夫 [Official Music Video].mp3 to loaded playlist: anime

Thu Nov 25 16:35:11 PST 2021
Added song: ./data/audio/Stand by Me.mp3 to loaded playlist: anime

Thu Nov 25 16:35:11 PST 2021
Added song: ./data/audio/crybaby.mp3 to loaded playlist: anime

Thu Nov 25 16:35:11 PST 2021
Added song: ./data/audio/廻廻奇譚 - Eve MV.mp3 to loaded playlist: anime

Thu Nov 25 16:35:11 PST 2021
Loaded anime from: data/playlist_data/anime.json

Thu Nov 25 16:35:11 PST 2021
Added song: ./data/audio/廻廻奇譚 - Eve MV.mp3 to loaded playlist: eve

Thu Nov 25 16:35:11 PST 2021
Added song: ./data/audio/心予報 - Eve MV.mp3 to loaded playlist: eve

Thu Nov 25 16:35:11 PST 2021
Added song: ./data/audio/お気に召すまま - Eve MV.mp3 to loaded playlist: eve

Thu Nov 25 16:35:11 PST 2021
Added song: ./data/audio/RADWIMPS - 大丈夫 [Official Music Video].mp3 to loaded playlist: eve

Thu Nov 25 16:35:11 PST 2021
Loaded eve from: data/playlist_data/eve.json

Thu Nov 25 16:35:11 PST 2021
Added song: ./data/audio/'I' Novel.mp3 to loaded playlist: more jpop

Thu Nov 25 16:35:11 PST 2021
Added song: ./data/audio/crybaby.mp3 to loaded playlist: more jpop

Thu Nov 25 16:35:11 PST 2021
Added song: ./data/audio/Official髭男dism - Pretender［Official Video］.mp3 to loaded playlist: more jpop

Thu Nov 25 16:35:11 PST 2021
Added song: ./data/audio/RADWIMPS - 大丈夫 [Official Music Video].mp3 to loaded playlist: more jpop

Thu Nov 25 16:35:11 PST 2021
Added song: ./data/audio/Stand by Me.mp3 to loaded playlist: more jpop

Thu Nov 25 16:35:11 PST 2021
Added song: ./data/audio/the peggies「センチメートル」Music Video.mp3 to loaded playlist: more jpop

Thu Nov 25 16:35:11 PST 2021
Added song: ./data/audio/YOASOBI「ラブレター」Official Music Video.mp3 to loaded playlist: more jpop

Thu Nov 25 16:35:11 PST 2021
Added song: ./data/audio/【Ado】うっせぇわ.mp3 to loaded playlist: more jpop

Thu Nov 25 16:35:11 PST 2021
Added song: ./data/audio/夜に駆ける.mp3 to loaded playlist: more jpop

Thu Nov 25 16:35:11 PST 2021
Added song: ./data/audio/群青.mp3 to loaded playlist: more jpop

Thu Nov 25 16:35:11 PST 2021
Loaded more jpop from: data/playlist_data/more jpop.json

Thu Nov 25 16:35:11 PST 2021
Added song: data/audio/Terraria Music - Boss 1.mp3 to loaded playlist: terraria

Thu Nov 25 16:35:11 PST 2021
Added song: data/audio/Terraria Music - Hallow.mp3 to loaded playlist: terraria

Thu Nov 25 16:35:11 PST 2021
Loaded terraria from: data/playlist_data/terraria.json

Thu Nov 25 16:35:11 PST 2021
Added song: data/audio/crybaby.mp3 to loaded playlist: testing-playlist

Thu Nov 25 16:35:11 PST 2021
Added song: data/audio/Super Sonic vs. Perfect Dark Gaia - Sonic Unleashed [OST].mp3 to loaded playlist: testing-playlist

Thu Nov 25 16:35:11 PST 2021
Loaded testing-playlist from: data/playlist_data/testing-playlist.json

Thu Nov 25 16:35:11 PST 2021
Added song: ./data/audio/'I' Novel.mp3 to loaded playlist: testing

Thu Nov 25 16:35:11 PST 2021
Added song: ./data/audio/crybaby.mp3 to loaded playlist: testing

Thu Nov 25 16:35:11 PST 2021
Added song: ./data/audio/Official髭男dism - Pretender［Official Video］.mp3 to loaded playlist: testing

Thu Nov 25 16:35:11 PST 2021
Loaded testing from: data/playlist_data/testing.json

Thu Nov 25 16:35:11 PST 2021
Added song: data/audio/'I' Novel.mp3 to loaded playlist: valid

Thu Nov 25 16:35:11 PST 2021
Added song: data/audio/crybaby.mp3 to loaded playlist: valid

Thu Nov 25 16:35:11 PST 2021
Added song: data/audio/Official髭男dism - Pretender［Official Video］.mp3 to loaded playlist: valid

Thu Nov 25 16:35:11 PST 2021
Added song: data/audio/RADWIMPS - 大丈夫 [Official Music Video].mp3 to loaded playlist: valid

Thu Nov 25 16:35:11 PST 2021
Added song: data/audio/Stand by Me.mp3 to loaded playlist: valid

Thu Nov 25 16:35:11 PST 2021
Added song: data/audio/Super Sonic vs. Perfect Dark Gaia - Sonic Unleashed [OST].mp3 to loaded playlist: valid

Thu Nov 25 16:35:11 PST 2021
Added song: data/audio/Terraria Music - Boss 1.mp3 to loaded playlist: valid

Thu Nov 25 16:35:11 PST 2021
Added song: data/audio/Terraria Music - Hallow.mp3 to loaded playlist: valid

Thu Nov 25 16:35:11 PST 2021
Added song: data/audio/the peggies「センチメートル」Music Video.mp3 to loaded playlist: valid

Thu Nov 25 16:35:11 PST 2021
Added song: data/audio/YOASOBI「ラブレター」Official Music Video.mp3 to loaded playlist: valid

Thu Nov 25 16:35:11 PST 2021
Added song: data/audio/【Ado】うっせぇわ.mp3 to loaded playlist: valid

Thu Nov 25 16:35:11 PST 2021
Added song: data/audio/お気に召すまま - Eve MV.mp3 to loaded playlist: valid

Thu Nov 25 16:35:11 PST 2021
Added song: data/audio/夜に駆ける.mp3 to loaded playlist: valid

Thu Nov 25 16:35:11 PST 2021
Added song: data/audio/廻廻奇譚 - Eve MV.mp3 to loaded playlist: valid

Thu Nov 25 16:35:11 PST 2021
Added song: data/audio/心予報 - Eve MV.mp3 to loaded playlist: valid

Thu Nov 25 16:35:11 PST 2021
Added song: data/audio/群青.mp3 to loaded playlist: valid

Thu Nov 25 16:35:11 PST 2021
Added song: data/audio/蒼のワルツ - Eve MV.mp3 to loaded playlist: valid

Thu Nov 25 16:35:11 PST 2021
Loaded valid from: data/playlist_data/valid.json

Thu Nov 25 16:35:15 PST 2021
Opened song: ./data/audio/'I' Novel.mp3

Thu Nov 25 16:35:23 PST 2021
Opened playlist: eve

Thu Nov 25 16:35:30 PST 2021
Added song: data/audio/'I' Novel.mp3 to incomplete playlist: A descriptive playlist name!

Thu Nov 25 16:35:31 PST 2021
Added song: data/audio/crybaby.mp3 to incomplete playlist: A descriptive playlist name!

Thu Nov 25 16:35:35 PST 2021
Removed song: data/audio/crybaby.mp3 incomplete playlist: log

Thu Nov 25 16:35:37 PST 2021
Added song: data/audio/RADWIMPS - 大丈夫 [Official Music Video].mp3 to incomplete playlist: log

Thu Nov 25 16:35:46 PST 2021
A playlist called: logging was created

Thu Nov 25 16:35:49 PST 2021
Opened playlist: logging

Thu Nov 25 16:35:55 PST 2021
Opened playlist: logging

Thu Nov 25 16:36:06 PST 2021
Removed song: data/audio/RADWIMPS - 大丈夫 [Official Music Video].mp3 playlist: logging

Thu Nov 25 16:36:08 PST 2021
Added song: data/audio/Super Sonic vs. Perfect Dark Gaia - Sonic Unleashed [OST].mp3 playlist: logging

Thu Nov 25 16:36:12 PST 2021
Saved logging to: data/playlist_data/logging.json

**Phase 4: Task 3**

If I had more time to work on this project, I would try to refactor my code base in the following ways:

* Create a Writable interface for any persistence dependent classes in my model package. If that happens, I can implement the writing methods inside of the Playlist class itself, instead of making a new PlaylistWriter object every time I want to write to file for a specific Playlist object. This change will ultimately reduce the amount of cluttered association relationships present in the current UML diagram. 
* Make certain GUI methods more robust, with more diverse, exception handling. A good example of this can be shown in the playlist creator screen. If the user leaves the playlist name field blank (empty string), the create playlist button will still create a file called ".json".
* The classes within the ui package, ending with "Screen" were all subtypes of Swing's JFrame class. I would have made some of the methods that add components to the JFrame and initialize the final frame to be displayed more abstract (in a interface). Methods that fit this change would be initializeFrame(), addComponents(), addListeners() inside an interface called Framable. Implementing this interface makes the blueprint for making new JFrame children classes easier to use.
