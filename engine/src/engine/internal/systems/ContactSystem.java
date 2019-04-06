package engine.internal.systems;

import engine.external.Entity;
import engine.external.component.Component;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collection;

public class ContactSystem extends System {

    private ArrayList<Pair<Entity, Entity>> myContactingEntities;

    public ContactSystem(Collection<Class<? extends Component>> requiredComponents) {
        super(requiredComponents);
        myContactingEntities = new ArrayList<>();
    }

    @Override
    protected void run() {
        //TODO: implement this
        for(Pair entityPair:myContactingEntities){

        }
    }
}
