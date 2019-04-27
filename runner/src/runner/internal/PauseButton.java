package runner.internal;

import javafx.animation.Animation;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PauseButton extends ImageView {
    private boolean myPlayingStatus = true;
    private Animation myAnimation;
    private AudioManager myAudioManager;
    private final Double X_LOCATION = 20.0;
    private final Double Y_LOCATION = 20.0;
    private static final Double WIDTH = 30.0;
    private static final Double HEIGHT = 30.0;

    public PauseButton(Animation animation, AudioManager audioManager){
        super(new Image("pause.png", WIDTH, HEIGHT, true, false));
        this.setLayoutX(X_LOCATION);
        this.setLayoutY(Y_LOCATION);
        myAnimation = animation;
        myAudioManager = audioManager;
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
        myAudioManager.pauseAllSound();
    }

    private void resumeGame(){
        myAnimation.play();
        myAudioManager.resumeAllSound();
    }

    public ImageView getPauseButton(){
        return this;
    }

    public Double getButtonX() {
        return X_LOCATION;
    }

    }