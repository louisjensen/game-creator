package EngineExampleCode;

import javafx.scene.Node;

import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;

/**
 * "Entity" in the ECS design pattern, defines any object (character, enemy, obstacle, etc.) that can show up in a game
 * Can have as many as possible components (collidable, touchable, movable, visible, etc.)
 * Components that are attached to a GameObject are stored in the field myComponents
 * Every type of component has its designated index, which is mapped to a bit index in the myEntityID of a GameObject
 * e.g. Collidable - index 0; Movable - 1;
 * A GameObject that is only collidable (say obstacle) has EntityID 000...01
 * A GameObject that's both collidable and movable (say Mario) has EntityID 0000...011
 * myUniqueID defines type of an GameObject: e.g. Mario, Spiny, SolidPlatform, BouncyPlatform
 */
public class GameObject {
    private Long myEntityID;
    private String myUniqueID;
    private Integer myGameLevel;
    private Integer myLives;
    private Boolean myGameWin;
    private Boolean myGameFail;
    private Node myNode;
    private Component[] myComponents;
    private ResourceBundle COMPONENT_INDEX_RESOURCE = ResourceBundle.getBundle("doc/plan/usecases/ComponentIndex.properties");


    public GameObject(long entityID, String uniqueID, int level, Node node) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        this.myEntityID = entityID;
        this.myUniqueID = uniqueID;
        this.myGameLevel = level;
        this.myGameWin = false;
        this.myGameFail = false;
        this.myComponents = new Component[3];
        this.myNode = node;
        initComponents();

    }

    public long getMyEntityID() {
        return myEntityID;
    }

    public String getMyUniqueID(){
        return myUniqueID;
    }

    public Node getMyNode(){
        return myNode;
    }

    public Component getComponent(int i){
        return myComponents[i];
    }

    public void setMyLives(int life){
        myLives = life;
    }

    public void subtractLife(){
        myLives-=1;
    }

    /**
     * Equip a GameObject with necessary components based on its myEntityID
     * TODO: error handling
     */
    private void initComponents() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        for(int i = 0; i<32; i++){
            if(((((long)1)<<i)&myEntityID)!=0){
                Component component = (Component)Class.forName(COMPONENT_INDEX_RESOURCE.getString(Integer.toString(i))).getConstructor().newInstance();
                component.setMyGameObject(this);
                myComponents[i]=component;
            }
        }
    }


}
