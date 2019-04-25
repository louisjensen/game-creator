package engine.external.component;

import java.io.Serializable;

/**
 *
 * Containers for storing necessary data of Entities
 * @param <T> data to be stored; T can be any type (e.g. String, Double, Boolean, Collection, etc.)
 */
public abstract class Component<T> implements Serializable {
    protected T myValue;
    protected T myOriginalValue;

    public Component(T value){
        myValue = value;
        myOriginalValue = value;
    }

    public void setValue(T value){
        myValue = value;
    }

    public T getValue(){
        return myValue;
    }

    public void resetToOriginal(){
        myValue = myOriginalValue;
    }
}
