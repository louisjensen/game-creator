package ui.main;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainTester extends Application {

    @Override
    public void start(Stage stage) {
        MainGUI testGUI = new MainGUI();
        testGUI.launch();
    }
}
