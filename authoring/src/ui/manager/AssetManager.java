package ui.manager;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
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
import java.util.HashSet;
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
    private static final String IMAGES_TITLE_KEY = "ImagesTitle";

    private static final double SPACING = 10;
    private static final int STAGE_WIDTH = 400;
    private static final int STAGE_HEIGHT = 300;
    private static final int IMAGE_SCROLLPANE_HEIGHT = 130;
    private static final Insets INSETS = new Insets(SPACING, SPACING, SPACING, SPACING);

    public AssetManager(TestEntity entity) {
        myEntity = entity;

        initializeVariables();
        initializeStage();
        fillImageExtensionSet();

        VBox vbox = new VBox();
        ScrollPane imagePane = generateImageDisplayHBox();
        TitledPane imageTitlePane = new TitledPane();
        imageTitlePane.setPrefHeight(IMAGE_SCROLLPANE_HEIGHT);
        imageTitlePane.setContent(imagePane);
        imageTitlePane.setText(myResources.getString(IMAGES_TITLE_KEY));
        vbox.getChildren().add(imageTitlePane);
        myBorderPane.setCenter(vbox);
    }

    private ScrollPane generateImageDisplayHBox() {
        HBox hbox = new HBox();
        //hbox.setPadding(INSETS);
        hbox.setSpacing(SPACING);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPadding(INSETS);
        scrollPane.setFitToHeight(true);
        scrollPane.setContent(hbox);
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

        return scrollPane;
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
        this.setWidth(STAGE_WIDTH);
        this.setHeight(STAGE_HEIGHT);
        Scene scene = new Scene(myBorderPane);
        this.setScene(scene);
    }

    private void fillImageExtensionSet(){
        for(String s: myResources.getString(EXTENSION_RESOURCE_KEY).split(",")){
            myImageExtensions.add(s);
        }
    }
}
