package frontend.header;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import frontend.Utilities;

import java.util.ResourceBundle;

public class HeaderBar {
    private Pane myHeaderLayout;
    private ResourceBundle myLanguageBundle;

    public HeaderBar() {
        myLanguageBundle = ResourceBundle.getBundle("languages/English");
        myHeaderLayout = new BorderPane();
        initializeLayouts();
    }

    public Pane getHeaderLayout() {
        return myHeaderLayout;
    }

    private void initializeLayouts() {
        Label title = new Label(Utilities.getValue(myLanguageBundle, "titleText"));
        BorderPane.setAlignment(title, Pos.TOP_CENTER);
        StackPane headerLayout = new StackPane();
        BorderPane titleLayout = new BorderPane();
        titleLayout.setCenter(title);
        headerLayout.getChildren().addAll(titleLayout);
        myHeaderLayout = headerLayout;
    }

}
