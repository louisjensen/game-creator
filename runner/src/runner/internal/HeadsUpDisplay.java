package runner.internal;

import javafx.scene.Node;
import javafx.scene.text.Text;

public class HeadsUpDisplay extends Node {
    private Double myLives;
    private Double myScore;
    private Double myLevel;
    private Text myLabel;
    private double xPosition;
    private static final double DEFAULT_Y = 30.0;

    public HeadsUpDisplay(int width){
        myLives = 3.0;
        myLevel = 1.0;
        myScore = 0.0;
        xPosition = 20;
        Text text = new Text ("ByteMe   " +
                "   Level: " + myLevel.intValue() +
                "   Score: " + myScore.intValue() +
                "   Lives: " +myLives.intValue());
        text.setId("HeadsUpDisplay");
        myLabel = text;
        myLabel.setLayoutX(xPosition);
        myLabel.setLayoutY(DEFAULT_Y);
    }

    public void updateLabel(){
        myLabel.setText("ByteMe   " +
                "   Level: " + myLevel.intValue() +
                "   Score: " + myScore.intValue() +
                "   Lives: " +myLives.intValue());
    }

    public Double getX(){
        return xPosition;
    }

    public Text getLabel(){
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
