package ui.panes;

import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.util.List;

class PropertiesPane extends TitledPane {

    private static final String PROP_TYPE_EXT = " Properties";

    PropertiesPane(String type, List<String> list) {
        GridPane optionsPane = createGridPane(list);
        this.setText(type + PROP_TYPE_EXT);
        this.setContent(optionsPane);
    }

    private GridPane createGridPane(List<String> options) {
        GridPane gridlist = new GridPane();
        for (int i = 0; i < options.size(); i++) {
            HBox newBox = new HBox();

            newBox.getChildren().add(new Text(options.get(i)));
            gridlist.add(newBox, 0, i);
        }
        return gridlist;
    }
}
