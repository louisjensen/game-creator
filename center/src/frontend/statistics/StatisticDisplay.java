package frontend.statistics;

import data.external.DataManager;
import data.external.GameCenterData;
import data.external.UserScore;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.sql.SQLException;
import java.util.List;

public class StatisticDisplay {
    private BorderPane myDisplay;
    private List<UserScore> myScores;

    private static final int COLUMN_NUMBER = 3;

    public StatisticDisplay(GameCenterData data, DataManager manager) {
        try {
            myScores = manager.loadScores(data.getTitle(), data.getAuthorName());
        } catch (SQLException e) {
            // todo: fix this
        }
        initializeDisplay();
    }

    public Pane getDisplay() {
        return myDisplay;
    }

    private void initializeDisplay() {
        myDisplay = new BorderPane();
        GridPane scores = new GridPane();
        setUpScoresHeader(scores);
        setUpScoresBody(scores);
        myDisplay.setCenter(scores);
    }

    private void setUpScoresHeader(GridPane scores) {
        for(int i = 0; i < COLUMN_NUMBER; i++) {

        }
    }

    private void setUpScoresBody(GridPane scores) {

    }


}
