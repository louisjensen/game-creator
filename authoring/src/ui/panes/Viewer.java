package ui.panes;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;


public class Viewer extends ScrollPane {
    private StackPane myStackPane;
    private static final int CELL_SIZE = 50;
    private int myRoomWidth;
    private int myRoomHeight;


    public Viewer(int roomWidth, int roomHeight){
        myStackPane = new StackPane();
        myStackPane.setAlignment(Pos.TOP_LEFT);
        myStackPane.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                dragEvent.acceptTransferModes(TransferMode.ANY);
            }
        });
        myStackPane.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                Dragboard db = dragEvent.getDragboard();
                boolean success = false;
                if (db.hasImage()) {
                    ImageView view = new ImageView(db.getImage());

                    System.out.println("Mouse X" + dragEvent.getX());
                    System.out.println("Mouse Y: " + dragEvent.getY());
                    view.setTranslateX(snapToGrid(dragEvent.getX()));
                    view.setTranslateY(snapToGrid(dragEvent.getY()));
                    System.out.println("View X: " + view.getTranslateX());
                    System.out.println("View Y: " + view.getTranslateY());
                    addImage(view);

                }
                /* let the source know whether the string was successfully
                 * transferred and used */
                dragEvent.setDropCompleted(success);
            }
        });
        setRoomSize(roomWidth, roomHeight);
        addGridLines();
        this.setContent(myStackPane);
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
            result = value + valueRemainder;
        }
        else{
            result = value - valueRemainder;
        }
        return result;

    }

    private void addImage(ImageView imageView){
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
            System.out.println("Y Coordinate: " + y);
            //System.out.println("Drew line");
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
