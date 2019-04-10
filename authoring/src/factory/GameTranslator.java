package factory;

import engine.external.Entity;
import engine.external.Level;
import runner.external.Game;
import ui.AuthoringEntity;
import ui.AuthoringLevel;
import ui.manager.ObjectManager;

public class GameTranslator {

    private Game myGameBasis;
    private ObjectManager myObjectManager;

    public GameTranslator(Game gameBasis, ObjectManager objectManager) {
        myGameBasis = gameBasis;
        myObjectManager = objectManager;
    }


    public Game translate() {
        Game translatedGame = new Game();
        //translatedGame.setTitle(myGameBasis.getTitle()); //TODO
        //translatedGame.setDescription(myGameBasis.getDescription());
        //translatedGame.setIcon(myGameBasis.getIcon());

        for (AuthoringLevel authLevel : myObjectManager.getLevels()) {
            translatedGame.addLevel(translateLevel(authLevel));
        }

        return translatedGame;
    }

    private Level translateLevel(AuthoringLevel authLevel) {
        Level newLevel = new Level();

        for (AuthoringEntity authEntity : authLevel.getEntities()) {
            newLevel.addEntity(translateEntity(authEntity));
        }

        return newLevel;
    }

    private Entity translateEntity(AuthoringEntity authEntity) {
        Entity newEntity = new Entity();
        Entity backingEntity = authEntity.getBackingEntity();

        // TODO account for new property types later




        return newEntity;
    }

}
