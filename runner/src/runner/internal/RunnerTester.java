package runner.internal;

import javafx.application.Application;
import javafx.stage.Stage;
import runner.external.GameRunner;

public class RunnerTester extends Application {

    @Override
    public void start (Stage primaryStage) throws Exception{
        System.out.println("testing");
        GameRunner runner = new GameRunner(null, 200, 200, primaryStage);
        runner.display();
    }

}
