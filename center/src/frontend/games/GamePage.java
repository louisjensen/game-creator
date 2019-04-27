package frontend.games;

import data.external.DataManager;
import data.external.GameCenterData;
import frontend.Utilities;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GamePage {
    private GameCenterData myData;
    private BorderPane myDisplay;
    private static final String TITLE_SELECTOR = "titlefont";
    private static final String SUBTITLE_SELECTOR = "subtitlefont";
    private static final String BODY_SELECTOR = "bodyfont";
    private static final String SCROLLER_SELECTOR = "scroller";
    private static final String GAME_PAGE_SELECTOR = "gamepage";
    private static final double POPUP_WIDTH = 750;
    private static final double POPUP_HEIGHT = 500;
    private static final double IMAGE_SIZE = 500;
    private static final double WRAP_OFFSET = 20;
    private static final double SCROLL_OFFSET = 20;
    private static final Color BACKGROUND_COLOR = Color.rgb(46, 43, 51);
    private DataManager myManager;

    public GamePage(GameCenterData data, DataManager manager) {
        myData = data;
        myManager = manager;
        initializeDisplay();
    }

    public void display() {
        ScrollPane scroller = new ScrollPane(myDisplay);
        scroller.getStylesheets().add("center.css");
        scroller.getStyleClass().add(SCROLLER_SELECTOR);
        scroller.setMaxHeight(POPUP_HEIGHT - SCROLL_OFFSET);
        Scene scene = new Scene(scroller, POPUP_WIDTH, POPUP_HEIGHT, BACKGROUND_COLOR);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.showAndWait();
    }

    public void playGameButton(GameCenterData data) {
        Utilities.launchGameRunner(data.getFolderName());
    }

    public void rateGameButton(GameCenterData data) {
        // todo: implement this method!
    }

    private void initializeDisplay() {
        myDisplay = new BorderPane();
        myDisplay.getStylesheets().add("center.css");
        myDisplay.getStyleClass().add(GAME_PAGE_SELECTOR);
        addHeader();
        addBody();
        addButtons();
    }

    private void addBody() {
        BorderPane contentPane = new BorderPane();
        contentPane.setTop(Utilities.getImagePane(myManager, myData.getImageLocation(), IMAGE_SIZE));
        Text body = new Text(myData.getDescription());
        body.getStyleClass().add(BODY_SELECTOR);
        body.setWrappingWidth(POPUP_WIDTH - WRAP_OFFSET);
        contentPane.setCenter(body);
        myDisplay.setCenter(contentPane);
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

    private void addButtons() {
        BorderPane buttonPane = new BorderPane();
        buttonPane.setCenter(Utilities.makeButtons(this, myData));
        myDisplay.setBottom(buttonPane);
    }

}
