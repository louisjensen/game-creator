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
    private static final String BUNDLE_NAME = "AssetManager";
    private Set<String> myImageExtensions;
    private String EXTENSION_RESOURCE_KEY = "AcceptableImageExtensions";

    public AssetManager(TestEntity entity) {
        myEntity = entity;
        myResources = ResourceBundle.getBundle(BUNDLE_NAME);
        myImageExtensions = new HashSet<>();
        fillImageExtensionSet();

        VBox vbox = new VBox();
        Scene scene = new Scene(vbox);
        this.setScene(scene);
        this.setResizable(false);
        HBox hbox = new HBox();

        File assetFolder = new File("authoring/Assets/Images");
        System.out.println("Made it past filepath");
        for(File temp : assetFolder.listFiles()){
            System.out.println(temp.getName());
            try {
                String extension = temp.getName().split("\\.")[1];
                if(myImageExtensions.contains(extension)){
                    ImageView imageView = new ImageView();
                    Image image = null;
                    try {
                        image = new Image( new FileInputStream(temp.getPath()));
                    } catch (FileNotFoundException e) {
                        //TODO DEAL WITH THIS
                    }
                    imageView.setImage(image);
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
        vbox.getChildren().add(hbox);
    }

    private void fillImageExtensionSet(){
        for(String s: myResources.getString(EXTENSION_RESOURCE_KEY).split(",")){
            myImageExtensions.add(s);
        }
    }
}
