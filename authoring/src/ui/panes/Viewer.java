package ui.panes;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class Viewer extends Pane {

    public Viewer(){
        this.setMinHeight(500);
        this.setMinWidth(500);

        Rectangle rect = new Rectangle();
        rect.setHeight(100);
        rect.setWidth(100);
        this.getChildren().add(rect);
        rect.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("Drag detected");
                Dragboard db = rect.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                try {
                    Image image = new Image(new FileInputStream("authoring/assets/images/mario_block.png"), 50, 50, false, false);
                    content.putImage(image);
                    db.setContent(content);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });

        this.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                dragEvent.acceptTransferModes(TransferMode.ANY);
            }
        });
        this.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                System.out.println("onDragDropped");
                /* if there is a string data on dragboard, read it and use it */
                Dragboard db = dragEvent.getDragboard();
                boolean success = false;
                if (db.hasImage()) {
                    ImageView view = new ImageView(db.getImage());
                    view.setFitWidth(50);
                    view.setFitHeight(50);
                    addImage(view);
                    view.setX(dragEvent.getX() - 25);
                    view.setY(dragEvent.getY() - 25);
                }
                /* let the source know whether the string was successfully
                 * transferred and used */
                dragEvent.setDropCompleted(success);

            }
        });
    }

    private void addImage(ImageView image){
        this.getChildren().add(image);
    }
}
