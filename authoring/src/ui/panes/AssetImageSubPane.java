package ui.panes;

import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;

public class AssetImageSubPane extends TitledPane {
    private static final int IMAGE_SIZE = 20;

    public AssetImageSubPane(String title, ImageView image){
        this.setText(title);
        this.setContent(image);
        image.setFitWidth(IMAGE_SIZE);
        image.setFitHeight(IMAGE_SIZE);
        this.setCollapsible(false);
    }

}
