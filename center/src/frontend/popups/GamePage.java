package frontend.popups;

import data.external.DataManager;
import data.external.GameCenterData;
import frontend.Utilities;
import frontend.ratings.RatingList;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class GamePage extends Popup {
    private static final String BODY_SELECTOR = "bodyfont";
    private static final String SCROLLER_SELECTOR = "scroller";
    private static final double POPUP_WIDTH = 750;
    private static final double POPUP_HEIGHT = 500;
    private static final double IMAGE_SIZE = 500;
    private static final double WRAP_OFFSET = 20;
    private static final double SCROLL_OFFSET = 20;

    private String myCurrentUser;
    private GameCenterData myData;

    public GamePage(GameCenterData data, DataManager manager, String user) {
        super(manager);
        myData = data;
        myCurrentUser = user;
        initializeDisplay();
        display();
    }

    public void playGameButton(GameCenterData data) {
        Utilities.launchGameRunner(data.getFolderName());
    }

    public void rateGameButton(GameCenterData data) {
        new RatingScreen(data, myManager, myCurrentUser);
    }

    @Override
    protected void display() {
        showScene(myDisplay, POPUP_WIDTH, POPUP_HEIGHT);
    }

    @Override
    protected void addBody() {
        BorderPane contentPane = new BorderPane();
        contentPane.setTop(Utilities.getImagePane(myManager, myData.getImageLocation(), IMAGE_SIZE));
        Text body = new Text(myData.getDescription());
        body.getStyleClass().add(BODY_SELECTOR);
        body.setWrappingWidth(POPUP_WIDTH - WRAP_OFFSET);
        contentPane.setCenter(body);
        RatingList gameRatings = new RatingList(myData, myManager);
        contentPane.setBottom(gameRatings.getDisplay());
        ScrollPane scroller = new ScrollPane(contentPane);
        scroller.getStylesheets().add("center.css");
        scroller.getStyleClass().add(SCROLLER_SELECTOR);
        scroller.setMaxHeight(POPUP_HEIGHT - SCROLL_OFFSET);
        myDisplay.setCenter(scroller);
    }

    @Override
    protected void addHeader() {
        addTitleAndSubtitle(myDisplay, myData.getTitle(), myData.getAuthorName(), POPUP_WIDTH);
    }

    @Override
    protected void addButtons() {
        BorderPane buttonPane = new BorderPane();
        buttonPane.setCenter(Utilities.makeButtons(this, myData));
        myDisplay.setBottom(buttonPane);
    }

}
