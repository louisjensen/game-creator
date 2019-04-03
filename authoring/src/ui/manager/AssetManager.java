package ui.manager;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ui.TestEntity;
import ui.panes.AssetImageSubPane;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

public class AssetManager extends Stage {

    private TestEntity myEntity;
    private ResourceBundle myResources;
    private Set<String> myImageExtensions;
    private BorderPane myBorderPane;

    private static final String EXTENSION_RESOURCE_KEY = "AcceptableImageExtensions";
    private static final String BUNDLE_NAME = "AssetManager";
    private static final String ASSET_IMAGE_FOLDER_PATH = "authoring/Assets/Images";
    private static final int WIDTH = 200;
    private static final int HEIGHT = 300;

    public AssetManager(TestEntity entity) {
        myEntity = entity;

        initializeVariables();
        initializeStage();
        fillImageExtensionSet();

        VBox vbox = new VBox();
        HBox hbox = generateImageDisplayHBox();
        vbox.getChildren().add(hbox);
        myBorderPane.setCenter(vbox);
    }

    private HBox generateImageDisplayHBox() {
        HBox hbox = new HBox();
        File assetFolder = new File(ASSET_IMAGE_FOLDER_PATH);
        for(File temp : assetFolder.listFiles()){
            System.out.println(temp.getName());
            try {
                String extension = temp.getName().split("\\.")[1];
                if(myImageExtensions.contains(extension)){
                    ImageView imageView = createImageView(temp);
                    AssetImageSubPane subPane = new AssetImageSubPane(temp.getName().split("\\.")[0], imageView);
                    hbox.getChildren().add(subPane);
                }
            }
            catch (IndexOutOfBoundsException e){
                //this occurs when there is no file extension
                //this file should be skipped over and nothing should happen
                //this catch should be empty
            }
        }
        return hbox;
    }

    private ImageView createImageView(File temp) {
        ImageView imageView = new ImageView();
        try {
            Image image = new Image( new FileInputStream(temp.getPath()));
            imageView.setImage(image);
        } catch (FileNotFoundException e) {
            //TODO DEAL WITH THIS
        }
        return imageView;
    }

    private void initializeVariables(){
        myResources = ResourceBundle.getBundle(BUNDLE_NAME);
        myImageExtensions = new HashSet<>();
        myBorderPane = new BorderPane();
    }

    private void initializeStage(){
        this.setResizable(false);
        this.setWidth(WIDTH);
        this.setHeight(HEIGHT);
        Scene scene = new Scene(myBorderPane);
        this.setScene(scene);
    }

    private void fillImageExtensionSet(){
        for(String s: myResources.getString(EXTENSION_RESOURCE_KEY).split(",")){
            myImageExtensions.add(s);
        }
    }
}
