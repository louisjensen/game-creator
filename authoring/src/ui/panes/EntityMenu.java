package ui.panes;

import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;

public class EntityMenu extends TitledPane {
    private Accordion myAccordion;

    public EntityMenu(String title, int prefHeight, int prefWidth){
        this.setText(title);
        this.setCollapsible(false);
        this.setPrefHeight(prefHeight);
        this.setPrefWidth(prefWidth);
        myAccordion = new Accordion();
        this.setContent(myAccordion);
    }

    public void addDropDown(String title, Node contents){
        TitledPane newTitled = new TitledPane();
        newTitled.setText(title);
        newTitled.setContent(contents);
        myAccordion.getPanes().add(newTitled);
    }
}
