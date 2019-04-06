package ui_components.games;

import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameCard {
    private Pane myDisplay;
    public static final double DISPLAY_WIDTH = 300;
    public static final double DISPLAY_HEIGHT = 300;

    public GameCard() {
        initializeDisplay();
    }

    public Pane getDisplay() {
        return myDisplay;
    }

    private void initializeDisplay() {
        BorderPane tempDisplay = new BorderPane();
        tempDisplay.setBackground(Background.EMPTY);
        Rectangle display = new Rectangle(0, 0, DISPLAY_WIDTH, DISPLAY_HEIGHT);
        display.setFill(Color.WHITE);
        tempDisplay.setCenter(display);
        myDisplay = tempDisplay;
    }

}
