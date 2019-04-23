package runner.internal;

import javafx.animation.Animation;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PauseButton extends ImageView {
    private boolean myPlayingStatus = true;
    private Animation myAnimation;

    public PauseButton(Animation animation){
        super(new Image("pause.png", 30.0, 30.0, true, false));
        this.setLayoutX(30.0);
        this.setLayoutY(30.0);
        myAnimation = animation;
        this.setOnMouseClicked(event ->{
            toggleAnimation();
        });
    }

    private void toggleAnimation() {
        if(myPlayingStatus){
            pauseGame();
        } else {
            resumeGame();
        }
        myPlayingStatus = !myPlayingStatus;
    }

    private void pauseGame() {
        myAnimation.pause();
    }

    private void resumeGame(){
        myAnimation.play();
    }

    public ImageView getPauseButton(){
        return this;
    }

}