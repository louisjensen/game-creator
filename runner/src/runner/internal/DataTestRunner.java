package runner.internal;

import data.external.DatabaseEngine;
import runner.external.GameRunner;
import javafx.stage.Stage;

public class DataTestRunner {
    public static void main(String[] args){
        System.out.println("DataTestRunner");
        DatabaseEngine.getInstance().open();
        RunnerTester.main(args);
        //GameRunner runner = new GameRunner("game1");
    }
}
