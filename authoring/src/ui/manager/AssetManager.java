package ui.manager;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ui.TestEntity;
import ui.panes.AssetImageSubPane;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class AssetManager extends Stage {

    private TestEntity myEntity;
    private ResourceBundle myResources;
    private Set<String> myImageExtensions;
    private BorderPane myBorderPane;
    private ScrollPane myImagePane;
    private HBox myButtonHBox;
    private Image mySelectedImage;

    private static final String EXTENSION_RESOURCE_KEY = "AcceptableImageExtensions";
    private static final String BUNDLE_NAME = "AssetManager";
    private static final String ASSET_IMAGE_FOLDER_PATH = "authoring/Assets/Images";
    private static final String IMAGES_TITLE_KEY = "ImagesTitle";
    private static final String BUTTON_FAIL_KEY = "ButtonFailText";
    private static final String BUTTON_INFO = "Buttons";
    private static final String IO_ERROR = "IOError";
    private static final String ERROR_HEADER = "ErrorHeader";
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
        myImagePane = new ScrollPane();
        drawImageScrollPane();
        myButtonHBox = new HBox();
        createButtonPane();

        TitledPane imageTitlePane = new TitledPane();
        imageTitlePane.setPrefHeight(IMAGE_SCROLLPANE_HEIGHT);
        imageTitlePane.setContent(myImagePane);
        imageTitlePane.setText(myResources.getString(IMAGES_TITLE_KEY));
        vbox.getChildren().add(imageTitlePane);
        vbox.getChildren().add(myButtonHBox);
        myBorderPane.setCenter(vbox);
    }

    private HBox createButtonPane() {
        String buttonString = myResources.getString(BUTTON_INFO);
        String[] buttonInfo = buttonString.split(",");
        for(String s : buttonInfo){
            String[] textAndMethod = s.split(" ");
            Button button = makeButton(textAndMethod[0], textAndMethod[1]);
            myButtonHBox.getChildren().add(button);
        }
        return myButtonHBox;
    }

    private void drawImageScrollPane() {
        HBox hbox = new HBox();
        //hbox.setPadding(INSETS);
        hbox.setSpacing(SPACING);
        myImagePane.setPadding(INSETS);
        myImagePane.setFitToHeight(true);
        myImagePane.setContent(hbox);
        File assetFolder = new File(ASSET_IMAGE_FOLDER_PATH);
        for(File temp : assetFolder.listFiles()){
            System.out.println(temp.getName());
            try {
                String extension = temp.getName().split("\\.")[1];
                String lowerCaseExtension = extension.toLowerCase();
                if(myImageExtensions.contains(lowerCaseExtension)){
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

    private Button makeButton(String text, String methodName){
        Button temp = new Button();
        temp.setText(text);
        temp.setOnMouseClicked(e -> {
            try {
                Method method = this.getClass().getDeclaredMethod(methodName);
                method.invoke(this);
            } catch (Exception e1) {
                temp.setText(myResources.getString(BUTTON_FAIL_KEY));
            }});
        return temp;
    }

    private void handleBrowse(){
        Stage stage = new Stage();
        FileChooser chooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG files (*.PNG)", "*.PNG");
        FileChooser.ExtensionFilter extFilter2 = new FileChooser.ExtensionFilter("JPEG files (*.JPG)", "*.JPG");
        chooser.getExtensionFilters().addAll(extFilter2, extFilter);
        File selectedFile = chooser.showOpenDialog(stage);
        try {
            BufferedImage image = ImageIO.read(selectedFile);
            File saveToFile = new File(ASSET_IMAGE_FOLDER_PATH + "/" + selectedFile.getName());
            System.out.println(selectedFile.getPath());
            String[] split = selectedFile.getPath().split("\\.");
            String extension = split[split.length-1];
            ImageIO.write(image, extension, saveToFile);
            drawImageScrollPane();
        } catch (IOException e) {
            //TODO: Test that this works
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(myResources.getString(ERROR_HEADER));
            alert.setHeaderText(null);
            alert.setContentText(myResources.getString(IO_ERROR));
            alert.showAndWait();
        }

    }
}
