package ui.panes;

import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityMenu extends TitledPane {
    private VBox myVBox;
    private Map<String, ListView> myMap;

    public EntityMenu(String title){
        this.setText(title);
        this.setCollapsible(false);
        myVBox = new VBox();
        myMap = new HashMap<>();
        this.setContent(myVBox);
    }

    public void addDropDown(String title){
        TitledPane newTitled = new TitledPane();
        newTitled.setAlignment(Pos.CENTER);
        newTitled.setText(title);
        ListView listView = new ListView();
        newTitled.setContent(listView);
        myMap.put(title, listView);
        myVBox.getChildren().add(newTitled);
    }

    public void addToDropDown(String category, List<Pane> contentToAdd){
        ListView vBox = myMap.get(category);
        vBox.getItems().addAll(contentToAdd);
    }
}
