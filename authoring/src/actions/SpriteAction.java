package actions;

import engine.external.Entity;
import engine.external.component.SpriteComponent;

import java.util.function.Consumer;

public class SpriteAction extends StringAction {
    public SpriteAction(String pathname){
        setAction(pathname, SpriteComponent.class);
    }
}
