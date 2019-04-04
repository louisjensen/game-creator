package ui.panes;

import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

import java.util.List;

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

    public void addDropDown(String title, List<TitledPane> contents){
        TitledPane newTitled = new TitledPane();
        Accordion tempAccordion = new Accordion();
        tempAccordion.getPanes().addAll(contents);
        newTitled.setText(title);

        newTitled.setContent(tempAccordion);
        myAccordion.getPanes().add(newTitled);
    }
}
