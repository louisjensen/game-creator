package ui.panes;

import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.MapChangeListener;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import ui.*;

import java.io.FileInputStream;
import java.util.ResourceBundle;


public class Viewer extends ScrollPane {
    private StackPane myStackPane;
    private static final int CELL_SIZE = 50;
    private boolean isDragOnView;
    private ObjectProperty<Propertable> myAuthoringLevel;
    private AuthoringEntity myDraggedAuthoringEntity;
    private ObjectProperty<Propertable>  mySelectedEntity;
    private UserCreatedTypesPane myUserCreatedPane;
    private ResourceBundle myGeneralResources;
    private static final ResourceBundle myResources = ResourceBundle.getBundle("viewer");


    /**
     *
     * @param authoringLevel
     * @param userCreatedTypesPane
     * @param objectProperty
     */
    public Viewer(ObjectProperty<Propertable> authoringLevel, UserCreatedTypesPane userCreatedTypesPane, ObjectProperty objectProperty){
        myStackPane = new StackPane();
        myUserCreatedPane = userCreatedTypesPane;
        mySelectedEntity = objectProperty;
        myAuthoringLevel = authoringLevel;
        myAuthoringLevel.getValue().getPropertyMap().addListener((MapChangeListener<? super Enum, ? super String>) change ->
                handleChange(change));
        myStackPane.setAlignment(Pos.TOP_LEFT);
        myGeneralResources = ResourceBundle.getBundle("authoring_general");
        setupAcceptDragEvents();
        setupDragDropped();
        setRoomSize();
        addGridLines();
        this.setContent(myStackPane);
        this.getStyleClass().add("viewer-scroll-pane");
    }


    private void handleChange(MapChangeListener.Change<? extends Enum,? extends String> change) {
        if(change.wasAdded() && myResources.containsKey(change.getKey().toString())){
            Utility.makeAndCallMethod(myResources, change, this);
        }
        if(change.getKey().equals(LevelField.BACKGROUND)){
            System.out.println("Background change triggered");
        }
    }

    private void updateWidth(String width){
        Double widthDouble = Double.parseDouble(width);
        this.setPrefWidth(widthDouble);
        myStackPane.setPrefWidth(widthDouble);
    }

    private void updateHeight(String height){
        Double heightDouble = Double.parseDouble(height);
        this.setPrefHeight(heightDouble);
        myStackPane.setPrefHeight(heightDouble);
    }

    private void updateBackground(String filename){
        System.out.println("Trying to update background");
        String filepath = myGeneralResources.getString("images_filepath") + filename;
        FileInputStream fileInputStream = Utility.makeFileInputStream(filepath);
        Double roomHeight = this.getPrefHeight();
        Double roomWidth = this.getPrefWidth();
        Image image = new Image(fileInputStream, roomWidth, roomHeight, false, false);
        BackgroundImage backgroundImage = new BackgroundImage(image, null, null, null, null);
        myStackPane.setBackground(new Background(backgroundImage));
    }

    private void setupAcceptDragEvents() {
        myStackPane.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                dragEvent.acceptTransferModes(TransferMode.ANY);
            }
        });
    }

    private void setupDragDropped() {

        myStackPane.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                AuthoringEntity authoringEntity;
                if(isDragOnView){
                    authoringEntity = myDraggedAuthoringEntity;
                    isDragOnView = false;
                }
                else{
                    authoringEntity = myUserCreatedPane.getDraggedAuthoringEntity();
                    addImage(Utility.createImageWithEntity(authoringEntity));
                }
                authoringEntity.getPropertyMap().put(EntityField.X, "" + snapToGrid(dragEvent.getX()));
                authoringEntity.getPropertyMap().put(EntityField.Y, "" + snapToGrid(dragEvent.getY()));
                mySelectedEntity.setValue(authoringEntity);
            }
        });
    }

    /**
     * Snaps the point to the closest gridline
     * @param value int to be snapped
     * @return int that is snapped
     */
    private double snapToGrid(double value){
        double valueRemainder = value % CELL_SIZE;

        double result;
        if(valueRemainder >= CELL_SIZE/2){
            result = value + CELL_SIZE - valueRemainder;
        }
        else{
            result = value - valueRemainder;
        }
        return result;

    }

    private void addImage(ImageWithEntity imageView){
        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                mySelectedEntity.setValue(imageView.getAuthoringEntity());
            }
        });
        imageView.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Utility.setupDragAndDropImage(imageView);
                isDragOnView = true;
                myDraggedAuthoringEntity = imageView.getAuthoringEntity();
            }
        });
        myStackPane.getChildren().add(imageView);
    }

    private void setRoomSize(){
        Double height = Double.parseDouble(myAuthoringLevel.getValue().getPropertyMap().get(LevelField.HEIGHT));
        Double width = Double.parseDouble(myAuthoringLevel.getValue().getPropertyMap().get(LevelField.WIDTH));
        this.setPrefHeight(height);
        this.setPrefWidth(width);
        myStackPane.setMinWidth(width);
        myStackPane.setMinHeight(height);
    }

    private void addGridLines(){
        int height = (int) Math.round(Double.parseDouble(myAuthoringLevel.getValue().getPropertyMap().get(LevelField.HEIGHT)));
        int width = (int) Math.round(Double.parseDouble(myAuthoringLevel.getValue().getPropertyMap().get(LevelField.WIDTH)));
        Pane linesPane = new Pane();
        linesPane.setPrefHeight(height);
        linesPane.setPrefWidth(width);
        addHorizontalLines(linesPane, height, width);
        addVerticalLines(linesPane, height, width);
        myStackPane.getChildren().add(linesPane);
    }

    private void addHorizontalLines(Pane pane, int height, int width) {
        int x1 = 0;
        for(int k = 0; k < height/CELL_SIZE; k++){
            int y = k * CELL_SIZE;
            Line tempLine = new Line(x1, y, width, y);
            pane.getChildren().add(tempLine);
        }
    }

    private void addVerticalLines(Pane pane, int height, int width){
        int y1 = 0;
        int y2 = height;
        for(int k = 0; k < width/CELL_SIZE; k++){
            int x = k * CELL_SIZE;
            Line tempLine = new Line(x, y1, x, y2);
            pane.getChildren().add(tempLine);
        }
    }

}
