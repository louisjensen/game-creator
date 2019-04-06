package ui_components.header;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class UserPane {
    private static final String IMAGE_PATH = "center/data/profile_information/images/default.png";
    private String myName;
    private Pane myUserDisplay;

    public UserPane(String username) {
        // todo: make this actually use username
    }

    public UserPane() {
        // todo: This will be used when usernames are implemented
        // this("default");
        myName = "default";
        try {
            initializeDisplay();
        } catch (FileNotFoundException e) {
            System.out.println("Please enter a valid username"); // todo: fix this
        }
    }

    public Pane getDisplay() {
        return myUserDisplay;
    }

    private void initializeDisplay() throws FileNotFoundException {
        VBox buttons = makeButtonList();
        Image icon = new Image(new FileInputStream(IMAGE_PATH));
        ImageView profilePicture = new ImageView(icon);
        profilePicture.setPreserveRatio(true);
        profilePicture.setFitWidth(75);
        myUserDisplay = new HBox();
        myUserDisplay.getChildren().add(profilePicture);
        myUserDisplay.getChildren().add(buttons);
    }

    private VBox makeButtonList() { // todo: figure out the issue where making buttons changes the background color
        Button profileButton = new Button("Profile"); // todo: make these buttons
        Button settingsButton = new Button("Settings");
        Button logOutButton = new Button("Log Out");
        VBox buttonList = new VBox();
        buttonList.getChildren().addAll(profileButton, settingsButton, logOutButton);
        return buttonList;
    }

}
