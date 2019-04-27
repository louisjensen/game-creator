package frontend.ratings;

import data.external.GameCenterData;
import frontend.Utilities;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
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

    public void addRatingButton(GameCenterData data) {

    }

    public void cancelButton(GameCenterData data) {

    }

    private void initializeDisplay() {
        myDisplay = new BorderPane();
        addTitle();
        addRatingInput();
        addButtons();
    }

    private void addRatingInput() {
        BorderPane ratingOptions = new BorderPane();
        addStars(ratingOptions);
        addCommentBox(ratingOptions);
        myDisplay.setCenter(ratingOptions);
    }

    private void addButtons() {
        myDisplay.setBottom(Utilities.makeButtons(this, myData));
    }

    private void addStars(BorderPane pane) {
        BorderPane stars = new BorderPane();
        Text starTitle = new Text(Utilities.getValue(myLanguageBundle, "starTitle"));
        starTitle.getStyleClass().add(BODY_SELECTOR);
        BorderPane.setAlignment(starTitle, Pos.CENTER);
        stars.setTop(starTitle);
        StarBox visualStars = new StarBox();
        BorderPane.setAlignment(visualStars.getDisplay(), Pos.CENTER);
        stars.setCenter(visualStars.getDisplay());
        pane.setTop(stars);
    }

    private void addCommentBox(Pane pane) {
//
    }

    private void addTitle() {
        Utilities.addTitleAndSubtitle(myDisplay, Utilities.getValue(myLanguageBundle, "ratingTitle"), myData.getTitle());
    }

    private void display() {
        Utilities.showScene(myDisplay, RATING_WIDTH, RATING_HEIGHT);
    }

}
