package ui;

import javafx.application.Application;
import javafx.stage.Stage;

/*
 * Main running class to start the tunes.java application
 */
public class JavaTunesApp extends Application {

    // EFFECTS: launches the swing application
    public static void main(String[] args)  {
        Application.launch(args);
    }

    // EFFECTS: creates a new instance of the HomeScreen JFrame
    @Override
    public void start(Stage stage) throws Exception {
        new HomeScreen();

    }
}
