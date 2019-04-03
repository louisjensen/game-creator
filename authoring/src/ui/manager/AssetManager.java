package ui.manager;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import ui.TestEntity;
import ui.panes.AssetImageSubPane;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class AssetManager extends Stage {

    private TestEntity myEntity;

    public AssetManager(TestEntity entity) {
        myEntity = entity;
        BorderPane bp = new BorderPane();
        Scene scene = new Scene(bp);
        this.setScene(scene);
        this.setResizable(false);
        HBox hbox = new HBox();

        File assetFolder = new File("authoring/Assets/Images");
        System.out.println("Made it past filepath");
        for(File temp : assetFolder.listFiles()){
            System.out.println(temp.getPath());
            ImageView imageView = new ImageView();
            Image image = null;
            try {
                image = new Image( new FileInputStream(temp.getPath()));
            } catch (FileNotFoundException e) {
                //TODO DEAL WITH THIS
            }
            imageView.setImage(image);
            AssetImageSubPane subPane = new AssetImageSubPane(temp.getName(), imageView);
            hbox.getChildren().add(subPane);
        }
        bp.setCenter(hbox);
    }
}
