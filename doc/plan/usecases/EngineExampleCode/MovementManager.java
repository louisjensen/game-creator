package EngineExampleCode;

import java.util.List;

/**
 * Manager class that manages movable GameObjects
 */
public class MovementManager {

    private GameEngine myEngine;
    private Long myManagerID;
    private final Integer myComponentIndex = 1;
    private final Double myStepInterval = 1000.0;
    private List<GameObject> myGameObjects;



    public MovementManager(GameEngine engine){
        this.myEngine = engine;
        this.myManagerID = ((long)1)<<myComponentIndex;
    }

    public void update(List<GameObject> list){
        myGameObjects.clear();
        // only cares about movable GameObjects
        for(GameObject o:list){
            if((o.getMyEntityID()&myManagerID)==myManagerID){
                myGameObjects.add(o);
                // invokes MovementComponent to update GameObject's associated properties
                ((MovementComponent)(o.getComponent(myComponentIndex))).move(myStepInterval);
                myEngine.getMyCollisionManager().addMovedGameObjects(o);
                myEngine.addUpdatedGameObject(o);
            }
        }
    }
}
