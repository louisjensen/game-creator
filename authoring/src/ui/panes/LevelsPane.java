package ui.panes;

import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import ui.AuthoringLevel;
import ui.LevelField;
import ui.Propertable;
import ui.manager.ObjectManager;

/**
 * @author Harry Ross
 */
public class LevelsPane extends TitledPane {

    private ObjectManager myObjectManager;
    private ObjectProperty<Propertable> myCurrentLevel;

    private static final String LEVELS_PANE_TITLE = "Levels";

    public LevelsPane(ObjectManager manager, ObjectProperty<Propertable> currentLevel) {
        myObjectManager = manager;
        ListView<String> levelsList = new ListView<>();
        myCurrentLevel = currentLevel;

        this.setText(LEVELS_PANE_TITLE);
        this.setContent(new ScrollPane(levelsList));
        levelsList.setItems(myObjectManager.getLabelManager().getLabels(LevelField.LABEL));
        this.getStyleClass().add("prop-pane");
        levelsList.getSelectionModel().selectedItemProperty()
                .addListener((ChangeListener<? super String>) (change, oldVal, newVal) -> changeLevel(newVal));
    }

    private void changeLevel(String newVal) {
        if (!newVal.equals((myCurrentLevel.getValue()).getPropertyMap().get(LevelField.LABEL))) {
            for (AuthoringLevel level : myObjectManager.getLevels()) {
                if (level.getPropertyMap().get(LevelField.LABEL).equals(newVal))
                    myCurrentLevel.setValue(level);
            }
        }
    }
}
