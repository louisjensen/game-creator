package frontend.ratings;

import data.external.GameCenterData;
import frontend.Utilities;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ResourceBundle;

public class RatingScreen {
    private static final double RATING_WIDTH = 350;
    private static final double RATING_HEIGHT = 400;
    private static final String DEFAULT_LANGUAGE = "languages/English";
    private static final String BODY_SELECTOR = "bodyfont";

    private BorderPane myDisplay;
    private ResourceBundle myLanguageBundle;
    private GameCenterData myData;

    public RatingScreen(GameCenterData data) {
        myLanguageBundle = ResourceBundle.getBundle(DEFAULT_LANGUAGE);
        myData = data;
        initializeDisplay();
        display();
    }

    private void initializeDisplay() {
        myDisplay = new BorderPane();
        addTitle();
        addRatingInput();
        addButtons();
    }

    private void addRatingInput() {
        VBox ratingOptions = new VBox();
        addStars(ratingOptions);
        addCommentBox(ratingOptions);
        myDisplay.setCenter(ratingOptions);
    }

    private void addButtons() {
        myDisplay.setBottom(Utilities.makeButtons(this, myData));
    }

    private void addStars(Pane pane) {
        HBox stars = new HBox();
        Text starTitle = new Text();
        starTitle.getStyleClass().add(BODY_SELECTOR);
        stars.getChildren().add(starTitle);

    }

    private void addCommentBox(Pane pane) {

    }

    private void addTitle() {
        Utilities.addTitleAndSubtitle(myDisplay, Utilities.getValue(myLanguageBundle, "ratingTitle"), myData.getTitle());
    }

    private void display() {
        Utilities.showScene(myDisplay, RATING_WIDTH, RATING_HEIGHT);
    }

}
