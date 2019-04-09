package actions;

import engine.external.Entity;
import engine.external.component.DirectionComponent;

import java.util.function.Consumer;

public class DirectionAction extends NumericAction {
   public DirectionAction(ModifyType type, Integer direction){
       setAction(type, direction, DirectionComponent.class);
   }
}