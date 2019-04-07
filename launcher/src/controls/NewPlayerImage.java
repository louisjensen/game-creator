package controls;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.ResourceBundle;

public class NewPlayerImage extends ImageView {
    private static final String WELCOME_RESOURCE = "launcher_display";
    private static final String DEFAULT_KEY = "new_player";
    private static final String PRESSED_KEY = "pressed_new_player";

    private static final ResourceBundle myResources = ResourceBundle.getBundle(WELCOME_RESOURCE);
    private static final Image DEFAULT_IMAGE = new Image(myResources.getString(DEFAULT_KEY));
    private static final Image PRESSED_IMAGE = new Image(myResources.getString(PRESSED_KEY));
    public NewPlayerImage(){
       this.setImage(DEFAULT_IMAGE);
       this.setScaleX(1.5);
       this.setScaleY(1.5);
       this.setOnMousePressed(new EventHandler<MouseEvent>() {
           @Override
           public void handle(MouseEvent mouseEvent) {
               switchImage(PRESSED_IMAGE);
           }
       });
        this.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                switchImage(DEFAULT_IMAGE);
            }

        });
    }

    private void switchImage(Image switchedImage){
        this.setImage(switchedImage);
    }
}
