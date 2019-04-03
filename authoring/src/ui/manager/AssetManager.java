package ui.manager;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ui.Propertable;

public class AssetManager extends Stage {

    private Propertable myProp;

    public AssetManager(Propertable prop) { // When user select an image, use setImage method of Propertable to set sprite/background
        myProp = prop;
        BorderPane bp = new BorderPane();
        Scene scene = new Scene(bp);
        this.setScene(scene);
        this.setResizable(false);
    }

    private void setImageToSelected(String resourceName) {
        myProp.getPropertyMap().put("Image", resourceName);
    }
}
