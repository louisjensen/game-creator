package ui_components.header;

import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class HeaderBar {
    private Pane myHeaderLayout;
    private UserPane myCurrentUser;
    private static final String TITLE_FONT = "Arial";
    private static final int TITLE_FONT_SIZE = 50;
    private static final Paint FONT_COLOR = Color.WHITE;

    public HeaderBar() {
        myHeaderLayout = new BorderPane();
        myCurrentUser = new UserPane();
        initializeLayouts();
    }

    public Pane getHeaderLayout() {
        return myHeaderLayout;
    }

    private void initializeLayouts() {
        Text title = new Text("ByteMe Game Center");
        title.setFont(Font.font(TITLE_FONT, TITLE_FONT_SIZE));
        title.setFill(FONT_COLOR);
        BorderPane.setAlignment(title, Pos.CENTER);
        myHeaderLayout = new BorderPane(title);
    }

}
