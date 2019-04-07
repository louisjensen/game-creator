package pane;

import controls.WelcomeLabel;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class WelcomeDisplay extends HBox {
    private static final String MY_STYLE = "default.css";
    public WelcomeDisplay(){
        this.getStylesheets().add(MY_STYLE);
        this.getStyleClass().add("welcome-label");
        Label myLabel = new WelcomeLabel();
        this.getChildren().add(myLabel);
        this.setAlignment(Pos.CENTER);
    }
}
