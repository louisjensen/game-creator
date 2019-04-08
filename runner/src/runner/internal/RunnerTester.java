package runner.internal;

import engine.external.Level;
import engine.external.component.Component;
import engine.external.Entity;
import engine.external.component.PositionComponent;
import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.stage.Stage;
import runner.external.Game;
import runner.external.GameRunner;

import java.util.ArrayList;
import java.util.List;

public class RunnerTester extends Application {
    List<Entity> myDummyEntities = new ArrayList<>();

    @Override
    public void start (Stage primaryStage) throws Exception{
        //addDummyEntities();
        Game dummyGame = new Game();
        initializeGame(dummyGame);
        System.out.println("testing");
        GameRunner runner = new GameRunner(dummyGame);
    }

    private void initializeGame(Game dummyGame) {
        Level level1 = new Level();
        addDummyEntities(level1);
        dummyGame.addLevel(level1);

    }

    private void addDummyEntities(Level level) {
        Entity dummy1 = new Entity();
        Entity dummy2 = new Entity();
        Entity dummy3 = new Entity();
        dummy1.addComponent(new PositionComponent(new Point3D(20.0, 20.0, 0.0)));
        dummy2.addComponent(new PositionComponent(new Point3D(110.0, -20.0, 0.0)));
        dummy3.addComponent(new PositionComponent(new Point3D(80.0, 130.0, 0.0)));

        level.addEntity(dummy1);
        level.addEntity(dummy2);
        level.addEntity(dummy3);
    }

}
