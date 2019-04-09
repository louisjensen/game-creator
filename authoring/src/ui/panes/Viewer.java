package ui.panes;

import javafx.beans.property.ObjectProperty;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import ui.AuthoringEntity;
import ui.EntityField;
import ui.Propertable;
import ui.Utility;

import java.util.ResourceBundle;


public class Viewer extends ScrollPane {
    private StackPane myStackPane;
    private static final int CELL_SIZE = 50;
    private int myRoomWidth;
    private int myRoomHeight;
    private boolean isDragOnView;
    private AuthoringEntity myDraggedAuthoringEntity;
    private ObjectProperty<Propertable>  mySelectedEntity;
    private UserCreatedTypesPane myUserCreatedPane;
    private ResourceBundle myGeneralResources;


    /**
     *
     * @param roomWidth
     * @param roomHeight
     * @param userCreatedTypesPane
     */
    public Viewer(int roomWidth, int roomHeight, UserCreatedTypesPane userCreatedTypesPane, ObjectProperty objectProperty){
        myStackPane = new StackPane();
        myUserCreatedPane = userCreatedTypesPane;
        mySelectedEntity = objectProperty;
        myStackPane.setAlignment(Pos.TOP_LEFT);
        myGeneralResources = ResourceBundle.getBundle("authoring_general");
        setupAcceptDragEvents();
        setupDragDropped();
        setRoomSize(roomWidth, roomHeight);
        addGridLines();
        this.setContent(myStackPane);
        this.getStyleClass().add("viewer-scroll-pane");
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

    /**
     * Sets the size of the room (the size of the entire level)
     * @param width Desired width of the level
     * @param height Desired height of the level
     */
    public void setRoomSize(int width, int height){
        myStackPane.setMinWidth(width);
        myStackPane.setMinHeight(height);
        myRoomWidth = width;
        myRoomHeight = height;
    }

    private void addGridLines(){
        Pane linesPane = new Pane();
        linesPane.setPrefHeight(myRoomHeight);
        linesPane.setPrefWidth(myRoomWidth);
        addHorizontalLines(linesPane);
        addVerticalLines(linesPane);
        myStackPane.getChildren().add(linesPane);
    }

    private void addHorizontalLines(Pane pane) {
        int x1 = 0;
        int x2 = myRoomWidth;
        for(int k = 0; k < myRoomHeight/CELL_SIZE; k++){
            int y = k * CELL_SIZE;
            Line tempLine = new Line(x1, y, x2, y);
            pane.getChildren().add(tempLine);
        }
    }

    private void addVerticalLines(Pane pane){
        int y1 = 0;
        int y2 = myRoomHeight;
        for(int k = 0; k < myRoomWidth/CELL_SIZE; k++){
            int x = k * CELL_SIZE;
            Line tempLine = new Line(x, y1, x, y2);
            pane.getChildren().add(tempLine);
        }
    }

}
