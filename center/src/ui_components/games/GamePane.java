package ui_components.games;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class GamePane {
    private Pane myDisplay;

    public GamePane() {
        initializeDisplay();
    }

    private void initializeDisplay() {
        BorderPane gamePane = new BorderPane();
        gamePane.setPadding(new Insets(10, 100, 100, 100));
        Text subtitle = new Text("All Games");
        subtitle.setFill(Color.WHITE);
        subtitle.setTextAlignment(TextAlignment.CENTER);
        BorderPane.setAlignment(subtitle, Pos.CENTER);
        subtitle.setFont(new Font(32));
        gamePane.setTop(subtitle);
        gamePane.setCenter((new GameList(6)).getDisplay());
        myDisplay = gamePane;
    }

    public Pane getDisplay() {
        return myDisplay;
    }

}
