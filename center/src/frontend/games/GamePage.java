package frontend.games;

import data.external.DataManager;
import data.external.GameCenterData;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GamePage {
    private GameCenterData myData;
    private BorderPane myDisplay;
    private static final String TITLE_SELECTOR = "titlefont";
    private static final String SUBTITLE_SELECTOR = "subtitlefont";
    private static final String BODY_SELECTOR = "bodyfont";
    private static final double POPUP_WIDTH = 750;
    private static final double POPUP_HEIGHT = 500;
    private DataManager myManager;

    public GamePage(GameCenterData data, DataManager manager) {
        myData = data;
        myManager = manager;
        initializeDisplay();
    }

    public void display() {
        Scene scene = new Scene(myDisplay, POPUP_WIDTH, POPUP_HEIGHT);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.showAndWait();
    }

    private void initializeDisplay() {
        myDisplay = new BorderPane();
        myDisplay.getStylesheets().add("center.css");
        addHeader();
        addBody();
    }

    private void addBody() {

        Text body = new Text(myData.getDescription());
        body.getStyleClass().add(BODY_SELECTOR);

    }

    private void addHeader() {
        Text title = new Text(myData.getTitle());
        title.getStyleClass().add(TITLE_SELECTOR);
        Text author = new Text(myData.getAuthorName());
        author.getStyleClass().add(SUBTITLE_SELECTOR);
        VBox text = new VBox(title, author);
        text.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(text, Pos.CENTER);
        myDisplay.setTop(text);
    }

}
