package pane;

import controls.EnterGameButton;
import controls.HiddenField;
import controls.InformativeField;
import controls.PaneLabel;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import java.util.ResourceBundle;

public class UserLoginDisplay extends VBox {
    private static final String LOGIN_RESOURCE = "launcher_display";
    private static final ResourceBundle myResources = ResourceBundle.getBundle(LOGIN_RESOURCE);

    private static final String LOGIN_KEY = "login_display";

    private static final String LOGIN_BUTTON_KEY = "login_button";
    private static final String LOGIN_BUTTON_TEXT = myResources.getString(LOGIN_BUTTON_KEY);

    private static final String LOGIN_LABEL = "login_label";

    public UserLoginDisplay(){
        this.getStylesheets().add("default.css");
        String[] loginTextFields = myResources.getString(LOGIN_KEY).split(",");
        this.setSpacing(40);
        InformativeField myUserNameField = new InformativeField(loginTextFields[0]);
        HiddenField myPasswordField = new HiddenField(loginTextFields[1]);
        this.setAlignment(Pos.CENTER);
        this.getChildren().add(0, new PaneLabel(LOGIN_LABEL));
        this.getChildren().add(1, myUserNameField);
        this.getChildren().add(2,myPasswordField);
        this.getChildren().add(3,new EnterGameButton(LOGIN_BUTTON_TEXT, myUserNameField.accessValue(),
                myPasswordField.accessValue()));
    }
}
