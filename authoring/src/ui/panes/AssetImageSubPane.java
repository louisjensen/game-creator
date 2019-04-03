package ui.panes;

import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class AssetImageSubPane extends VBox {
    private static final int IMAGE_SIZE = 50;
    private static final int PANE_SIZE = IMAGE_SIZE + 10;

    public AssetImageSubPane(String title, ImageView image){
        this.setMaxSize(PANE_SIZE, PANE_SIZE);
        this.setPrefSize(PANE_SIZE, PANE_SIZE);
        image.setFitWidth(IMAGE_SIZE);
        image.setFitHeight(IMAGE_SIZE);


        Text text = new Text(title);
        this.setAlignment(Pos.CENTER);
        this.getChildren().add(image);
        this.getChildren().add(text);
    }

}
