package controls;

import javafx.animation.FadeTransition;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.util.ResourceBundle;

public class WelcomeLabel extends Label {
    private static final String WELCOME_RESOURCE = "launcher_display";
    private static final String WELCOME_LABEL_KEY = "general_welcome";
    private static final String FADE_DURATION_KEY = "fade_duration";

    private final String myWelcomeLabel;
    private final FadeTransition myFadeAnimation;

    public WelcomeLabel(){
        ResourceBundle myResources = ResourceBundle.getBundle(WELCOME_RESOURCE);
        myWelcomeLabel = myResources.getString(WELCOME_LABEL_KEY);

        double fadeDuration = Double.parseDouble(myResources.getString(FADE_DURATION_KEY));
        myFadeAnimation = new FadeTransition(Duration.millis(fadeDuration));
        //setUpDisplay();
        this.setText(myWelcomeLabel);
        this.getStylesheets().add("welcome.css");

    }
    private void setUpDisplay(){
        this.setText(myWelcomeLabel);
        myFadeAnimation.setNode(this);
        myFadeAnimation.setFromValue(0.0);
        myFadeAnimation.setToValue(1.0);
        myFadeAnimation.setCycleCount(1);
        myFadeAnimation.setAutoReverse(false);
    }


}
