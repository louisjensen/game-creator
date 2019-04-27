package frontend.games;

import data.external.GameCenterData;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class GamePage {
    private GameCenterData myData;
    private Pane myDisplay;
    private static final String TEXT_SELECTOR = "cardtext";
    private static final String TITLE_SELECTOR = "cardtitle";
    private static final double STAGE_WIDTH = 1250;
    private static final double STAGE_HEIGHT = 750;
    private static final Color BACKGROUND_COLOR = Color.rgb(46, 43, 51);

    public GamePage(GameCenterData data) {
        myData = data;
        initializeDisplay();
    }

    public Scene getScene() {
        return new Scene(myDisplay, STAGE_WIDTH, STAGE_HEIGHT, BACKGROUND_COLOR);
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
        title.getStyleClass().add(TITLE_SELECTOR);
        Text author = new Text(myData.getAuthorName());
        author.getStyleClass().add(TEXT_SELECTOR);
    }

}
