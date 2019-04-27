package frontend.games;

import data.external.GameCenterData;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class GamePage {
    private GameCenterData myData;
    private Pane myDisplay;

    public GamePage(GameCenterData data) {
        myData = data;
        initializeDisplay();
    }

    private void initializeDisplay() {
        myDisplay = new BorderPane();
        addHeader(myDisplay);
        addBody(myDisplay);
    }

    private void addBody(Pane myDisplay) {

    }

    private void addHeader(Pane myDisplay) {
        Text title = new Text(myData.getTitle());

    }

}
