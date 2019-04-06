package ui.panes;

import javafx.scene.control.TitledPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class EntityMenu extends TitledPane {
    private VBox myVBox;

    public EntityMenu(String title, int prefHeight, int prefWidth){
        this.setText(title);
        this.setCollapsible(false);
        this.setPrefHeight(prefHeight);
        this.setPrefWidth(prefWidth);
        myVBox = new VBox();
        this.setContent(myVBox);
    }

    public void addDropDown(String title, Pane contents){
        TitledPane newTitled = new TitledPane();
        newTitled.setText(title);
        newTitled.setContent(contents);
        myVBox.getChildren().add(newTitled);
    }
}
