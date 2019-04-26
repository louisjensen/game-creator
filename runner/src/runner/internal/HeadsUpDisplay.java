package runner.internal;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class HeadsUpDisplay extends Node {
    private Double myLives;
    private Double myScore;
    private Double myLevel;
    private Label myLabel;
    private static final double DEFAULT_X = 200.0;
    private static final double DEFAULT_Y = 50.0;

    public HeadsUpDisplay(){
        myLives = 3.0;
        myLevel = 1.0;
        myScore = 0.0;
        myLabel = new Label("Level: " + myLevel + "\nScore: " + myScore + "\nLives: " +myLives);

        myLabel.setLayoutX(DEFAULT_X);
        myLabel.setLayoutY(DEFAULT_Y);
    }

    public void updateLabel(){
        myLabel.setText("Level: " + myLevel + "\nScore: " + myScore + "\nLives: " +myLives);
    }

    public Double getX(){
        return DEFAULT_X;
    }

    public Label getLabel(){
        return myLabel;
    }

    public void updateLives(Double lives){
        myLives = lives;
    }

    public void updateLevel(Double level){
        myLevel = level;
    }

    public void updateScore(Double score){
        myScore = score;
    }

}
