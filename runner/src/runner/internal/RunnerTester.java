package runner.internal;

import engine.external.Level;
import engine.external.component.Component;
import engine.external.Entity;
import engine.external.component.XPositionComponent;
import engine.external.component.YPositionComponent;
import engine.external.component.ZPositionComponent;
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
//        //TODO refactor
//        dummy1.addComponent(new PositionComponent(new Point3D(20.0, 20.0, 0.0)));
//        dummy2.addComponent(new PositionComponent(new Point3D(110.0, -20.0, 0.0)));
//        dummy3.addComponent(new PositionComponent(new Point3D(80.0, 130.0, 0.0)));

        level.addEntity(dummy1);
        level.addEntity(dummy2);
        level.addEntity(dummy3);

        //TODO: Hey Ryan, the Position Component has been decomposed into XPositionComponent, YPositionComponent, ZPositionComponent. Please refactor accordingly :)
//        dummy1.addComponent(new PositionComponent(new Point3D(20.0, 20.0, 0.0)));
//        dummy2.addComponent(new PositionComponent(new Point3D(50.0, 50.0, 0.0)));
//        dummy3.addComponent(new PositionComponent(new Point3D(80.0, 80.0, 0.0)));
//
//        myDummyEntities.add(dummy1);
//        myDummyEntities.add(dummy2);
//        myDummyEntities.add(dummy3);
    }

}
