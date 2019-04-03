package ui.panes;

import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class AssetImageSubPane extends VBox {
    private static final int IMAGE_SIZE = 50;
    private static final int PANE_SIZE = IMAGE_SIZE + 10;
    private static String myFileName;

    /**
     *
     * @param title
     * @param image
     */
    public AssetImageSubPane(String title, ImageView image){
        formatPaneAndImage(image);
        Text text = new Text(title);
        text.setWrappingWidth(PANE_SIZE);
        this.getChildren().add(image);
        this.getChildren().add(text);
    }

    private void formatPaneAndImage(ImageView image) {
        this.setAlignment(Pos.CENTER);
        this.setMaxSize(PANE_SIZE, PANE_SIZE);
        this.setPrefSize(PANE_SIZE, PANE_SIZE);
        image.setFitWidth(IMAGE_SIZE);
        image.setFitHeight(IMAGE_SIZE);
    }
}
