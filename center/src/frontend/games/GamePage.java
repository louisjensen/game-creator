package frontend.games;

import data.external.GameCenterData;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class GamePage {
    private GameCenterData myData;
    private BorderPane myDisplay;
    private static final String TITLE_SELECTOR = "titlefont";
    private static final String TEXT_SELECTOR = "subtitlefont";
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
        myDisplay.getStylesheets().add("center.css");
        addHeader();
        addBody();
    }

    private void addBody() {

    }

    private void addHeader() {
        Text title = new Text(myData.getTitle());
        title.getStyleClass().add(TITLE_SELECTOR);
        Text author = new Text(myData.getAuthorName());
        author.getStyleClass().add(TEXT_SELECTOR);
        VBox text = new VBox(title, author);
        text.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(text, Pos.CENTER);
        myDisplay.setTop(text);
    }

}
