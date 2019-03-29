package EngineExampleCode;

/**
 * Component that manages a movable GameObject's positions and speeds
 * Interacts with MovementManager
 */
public class MovementComponent extends Component {
    private Double myXPos;
    private Double myYPos;
    private Double myXSpeed;
    private Double myYSpeed;

    public MovementComponent(GameObject o){
        super(o);
    }

    public Double getMyXPos() {
        return myXPos;
    }

    public void setMyXPos(Double myXPos) {
        this.myXPos = myXPos;
    }

    public Double getMyYPos() {
        return myYPos;
    }

    public void setMyYPos(Double myYPos) {
        this.myYPos = myYPos;
    }

    public Double getMyXSpeed() {
        return myXSpeed;
    }

    public void setMyXSpeed(Double myXSpeed) {
        this.myXSpeed = myXSpeed;
    }

    public Double getMyYSpeed() {
        return myYSpeed;
    }

    public void setMyYSpeed(Double myYSpeed) {
        this.myYSpeed = myYSpeed;
    }

    public void move(Double duration){
        myXPos+=myXSpeed*duration;
        myYPos+=myYSpeed*duration;
    }

}
