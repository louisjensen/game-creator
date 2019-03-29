package EngineExampleCode;

/**
 * Super class of all components that can be attached to a GameObject
 */
public abstract class Component {
    protected GameObject myGameObject;
    protected ActionManager myActionManager;

    public Component(GameObject o){
        myGameObject = o;
        myActionManager = new ActionManager(myGameObject,this);
    }

    public void setMyGameObject(GameObject o){
        myGameObject = o;
    }


}
