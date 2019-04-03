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
     * @param filename
     * @param image
     */
    public AssetImageSubPane(String filename, ImageView image){
        formatPaneAndImage(image);
        String titleNoExtension = filename.split("\\.")[0];
        Text text = new Text(titleNoExtension);
        text.setWrappingWidth(PANE_SIZE);
        myFileName = filename;
        System.out.println("Filename Saved: " + myFileName);
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

    /**
     * Called by the asset manager when this pane is clicked
     * @return String filename of the file including the extension
     */
    public String getAssetFileName(){
        System.out.println("Filename returned by subpane: " + myFileName);
        return myFileName;
    }

}
