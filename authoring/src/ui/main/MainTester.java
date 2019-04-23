package ui.main;

import data.external.DatabaseEngine;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainTester extends Application {

    @Override
    public void start(Stage stage) {
        MainGUI testGUI = new MainGUI();
        testGUI.launch();
    }

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        DatabaseEngine.getInstance().close();
    }
}
