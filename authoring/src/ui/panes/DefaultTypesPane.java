package ui.panes;

import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.ResourceBundle;

public class NewEntityTypePane extends VBox{
    private EntityMenu myEntityMenu;
    private ResourceBundle myResources;

    private static final String RESOURCE = "default_entity_type";
    private static final String TITLE_KEY = "Title";
    private static final String TABS = "Tabs";
    private static final String TABS_REGEX = ",";

    private static final int HEIGHT = 500;
    private static final int WIDTH = 300;

    public NewEntityTypePane()     {
        myResources = ResourceBundle.getBundle(RESOURCE);
        myEntityMenu = new EntityMenu(myResources.getString(TITLE_KEY), WIDTH, HEIGHT);
        populateEntityMenu();
        this.getChildren().add(myEntityMenu);
    }

    private void populateEntityMenu(){
        String[] tabNames = myResources.getString(TABS).split(TABS_REGEX);
        for(String s1 : tabNames){
            ArrayList<HBox> labelsList = new ArrayList<>();
            String[] tabContentKeys = myResources.getString(s1).split(TABS_REGEX);
            for(String s2 : tabContentKeys){
                System.out.println(s2);
                String[] contentDefaultInfo = myResources.getString(s2).split(TABS_REGEX);
                Label label = new Label(contentDefaultInfo[0]);
                HBox hbox = new HBox(label);
                labelsList.add(hbox);
            }
            VBox vBox = new VBox();
            vBox.getChildren().addAll(labelsList);
            myEntityMenu.addDropDown(s1, vBox);
        }

    }

}
