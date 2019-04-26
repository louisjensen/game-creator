package runner.internal;

import data.external.DataManager;
import data.external.DatabaseEngine;
import engine.external.Level;
import engine.external.component.*;
import engine.external.Entity;
import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import runner.external.Game;
import runner.external.GameRunner;

import java.util.ArrayList;
import java.util.List;

public class RunnerTester extends Application {
    List<Entity> myDummyEntities = new ArrayList<>();

    public static void main(String[] args){launch(args);}

    @Override
    public void start (Stage primaryStage) throws Exception{
        GameRunner runner = new GameRunner("YeetRevised3", "DefaultAuthor");
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        DatabaseEngine.getInstance().close();
    }

}


