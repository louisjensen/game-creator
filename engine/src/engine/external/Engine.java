package engine.external;

import engine.external.component.*;
import engine.internal.systems.*;
import engine.internal.systems.VoogaSystem;
import javafx.event.Event;
import javafx.scene.input.KeyCode;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Engine {
    //TODO: Exception Handling
    private final ResourceBundle SYSTEM_COMPONENTS_RESOURCES = ResourceBundle.getBundle("SystemRequiredComponents");
    private final ResourceBundle SYSTEM_ORDER_RESOURCES = ResourceBundle.getBundle("SystemUpdateOrder");
    private final String SYSTEMS_PACKAGE_PATH = "engine.internal.systems.";
    private final String COMPONENTS_PACKAGE_PATH = "engine/external/component/";


    protected Collection<Entity> myEntities;
    protected Collection<IEventEngine> myEvents;
    private HashMap<Integer,VoogaSystem> mySystems;

    public Engine(Level level) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        myEntities = level.getEntities();
        myEvents = level.getEvents();
        initSystemMap();
    }

    public Collection<Entity> updateState(Collection<KeyCode> inputs){
        for(int i = 0; i<SYSTEM_ORDER_RESOURCES.keySet().size(); i++){
            mySystems.get(i).update(myEntities,inputs);
        }
        return this.getEntities();
    }

    public Collection<Entity> getEntities(){
        return Collections.unmodifiableCollection(myEntities);
    }

    private void initSystemMap() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        mySystems = new HashMap<>();
        Enumeration<String> enumSystems= SYSTEM_COMPONENTS_RESOURCES.getKeys();
        while (enumSystems.hasMoreElements()){
            initSystem(enumSystems.nextElement());
        }
    }

    private void initSystem(String systemName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException {
        Class systemClazz = Class.forName(this.getClass().getModule(),SYSTEMS_PACKAGE_PATH+systemName);
        Collection<Class<?extends Component>> systemComponents = retrieveComponentClasses(systemName);
        if(systemClazz== EventHandlerSystem.class){
            mySystems.put(Integer.valueOf(SYSTEM_ORDER_RESOURCES.getString(systemName)),(VoogaSystem) systemClazz.getConstructor(new Class[]{Collection.class,Engine.class,Collection.class}).newInstance(systemComponents,this,myEvents));
        }else{
            mySystems.put(Integer.valueOf(SYSTEM_ORDER_RESOURCES.getString(systemName)),(VoogaSystem) systemClazz.getConstructor(new Class[]{Collection.class,Engine.class}).newInstance(systemComponents,this));
        }
    }

    @SuppressWarnings({"unchecked"})
    private Collection<Class<? extends Component>> retrieveComponentClasses(String systemName) throws ClassNotFoundException {
        String[] componentArr = SYSTEM_COMPONENTS_RESOURCES.getString(systemName).split(",");
        ArrayList<Class<? extends Component>> componentList = new ArrayList<>();
        for(String component:componentArr){
            componentList.add((Class<? extends Component>)Class.forName(this.getClass().getModule(),component));
        }
        return componentList;
    }
}
