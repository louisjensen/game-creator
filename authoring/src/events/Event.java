package engine.external;


/**
 * Events are intended for creating/handling custom logic that is specific to a game, and cannot be reasonably anticipated by the engine beforehand
 *
 * @author Lucas Liu
 * @author Feroze Mohideen
 */
public abstract class Event {
    //TODO: Constructor
    //TODO: store conditions

    public void execute() {
        if (conditionsMet()) {
            executeActions();
        }
    }

    private boolean conditionsMet() {
        //TODO: implement
        return false;
    }

    private void executeActions() {
        //TODO: implement

    }
}
