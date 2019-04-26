package runner.internal;

import javafx.scene.Node;

public class HeadsUpDisplay extends Node {
    private Double myLives;
    private Double myScore;
    private Double myLevel;

    public HeadsUpDisplay(){
        //defaults
        myLives = 3.0;
        myLevel = 1.0;
        myScore = 0.0;
    }

    public void update(Double lives, Double level, Double score){
        //WILL BE CALLED IN LOOP
        myLives = lives;
        myLevel = level;
        myScore = score;
    }


}
