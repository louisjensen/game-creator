package ui.manager;

import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

import java.util.List;

public class TypeManager extends TitledPane {
    private Accordion myAccordion;

    public TypeManager(String title, int prefHeight, int prefWidth){
        this.setText(title);
        this.setPrefHeight(prefHeight);
        this.setPrefWidth(prefWidth);
        myAccordion = new Accordion();
        this.setContent(myAccordion);
    }

    public void addDropDown(String title, List<TitledPane> contents){
        TitledPane newTitled = new TitledPane();
        newTitled.setText(title);
        VBox tempVBox = new VBox();
        tempVBox.getChildren().addAll(contents);
        newTitled.setContent(tempVBox);
    }
}
