package ui_components.games;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GameCard {
    private Pane myDisplay;
    public static final double DISPLAY_WIDTH = 300;
    public static final double DISPLAY_HEIGHT = 300;
    private static final String DEFAULT_IMAGE_LOCATION = "center/data/game_information/images/default_game.png";

    public GameCard() {
        initializeDisplay();
    }

    public Pane getDisplay() {
        return myDisplay;
    }

    private void initializeDisplay() {
        BorderPane tempDisplay = new BorderPane();
        StackPane cardContents = null;
        try {
            cardContents = fillCardContents();
        } catch (FileNotFoundException e) {
            // todo: handle this error
        }
        tempDisplay.setCenter(cardContents);
        myDisplay = tempDisplay;
    }

    private StackPane fillCardContents() throws FileNotFoundException {
        StackPane cardContents = new StackPane();
        addBackground(cardContents);
        addForeGround(cardContents);
        return cardContents;
    }

    private void addBackground(Pane pane) {
        BorderPane background = new BorderPane();
        background.setBackground(Background.EMPTY);
        Rectangle display = new Rectangle(0, 0, DISPLAY_WIDTH, DISPLAY_HEIGHT);
        display.setFill(Color.WHITE);
        background.setCenter(display);
        pane.getChildren().add(background);
    }

    private void addForeGround(Pane pane) throws FileNotFoundException {
        BorderPane foreground = new BorderPane();
        addTitleContent(foreground);
        addImageContent(foreground);
        addButtons(foreground);
        pane.getChildren().add(foreground);
    }

    private void addButtons(BorderPane foreground) {
        Button readMore = new Button("Read More");
        readMore.setPrefWidth(100);
        Button play = new Button("Play");
        play.setPrefWidth(100);
        HBox buttons = new HBox(readMore, play);
        buttons.setSpacing(15);
        buttons.setAlignment(Pos.CENTER);
        Text spacing = new Text("");
        BorderPane buttonPane= new BorderPane();
        buttonPane.setCenter(buttons);
        buttonPane.setBottom(spacing);
        foreground.setBottom(buttonPane);
    }

    private void addImageContent(BorderPane foreground) throws FileNotFoundException {
        ImageView noGameFound = new ImageView(new Image(new FileInputStream(DEFAULT_IMAGE_LOCATION)));
        noGameFound.setPreserveRatio(true);
        noGameFound.setFitWidth(150);
        BorderPane imagePane = new BorderPane();
        imagePane.setCenter(noGameFound);
        BorderPane contentPane = new BorderPane();
        contentPane.setTop(imagePane);
        Text imageDescription = new Text("This is a fun platforming game that has lots of cool stuff to do! It was made in our very own authoring environment.");
        imageDescription.setWrappingWidth(DISPLAY_WIDTH);
        imageDescription.setTextAlignment(TextAlignment.CENTER);
        contentPane.setCenter(imageDescription);
        foreground.setCenter(contentPane);
    }

    private void addTitleContent(BorderPane foreground) {
        Text title = new Text("Title Text");
        title.setFont(new Font(24));
        BorderPane titlePane = new BorderPane();
        titlePane.setCenter(title);
        foreground.setTop(titlePane);
    }

}
