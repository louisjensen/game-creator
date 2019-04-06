package ui.manager;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import ui.Utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class GroupManager extends Stage {

    private ObjectManager myObjectManager;
    private ListView<String> myListView;

    public GroupManager(ObjectManager objectManager) {
        this.setResizable(false);
        this.setTitle("Group Manager");
        myObjectManager = objectManager;
        myListView = new ListView<>();
        ScrollPane scrollpane = new ScrollPane(createListContent());

        Map<String, List<String>> instructions = new TreeMap<>();
        instructions.put("label", new ArrayList<>(Collections.singletonList("Add or Remove a Group")));
        instructions.put("sub-label", new ArrayList<>(Collections.singletonList("Double-click a Group to rename")));

        Button addButton = new Button("Add");
        addButton.setOnAction(event -> newGroupPrompt());
        Button removeButton = new Button("Remove");
        removeButton.setOnAction(event -> removeLabel());
        Button okButton = new Button("Close");
        okButton.setOnAction(event -> this.close());

        this.setScene(Utility.createDialogPane(Utility.createLabelsGroup(instructions), new HBox(scrollpane),
                new ArrayList<>(Arrays.asList(addButton, removeButton, okButton))));
    }

    private HBox createListContent() {
        HBox contentBox = new HBox();
        contentBox.getChildren().add(myListView);
        myListView.setEditable(true);
        myListView.setCellFactory(TextFieldListCell.forListView());
        myListView.setItems(myObjectManager.getLabelManager().getLabels("Group"));
        myListView.setOnEditCommit(event -> editLabel(event));
        return contentBox;
    }

    private void addNewLabel(String newLabel) {
        myObjectManager.getLabelManager().addLabel("Group", newLabel);
    }

    private void editLabel(ListView.EditEvent<String> event) {
        String newVal = event.getNewValue();
        myListView.getItems().set(event.getIndex(), newVal);
    }

    private void removeLabel() {
        if (!myListView.getSelectionModel().getSelectedItems().isEmpty()) {
            String badLabel = myListView.getSelectionModel().getSelectedItems().get(0);
            myObjectManager.getLabelManager().removeLabel("Group", badLabel);
        }
    }

    private void newGroupPrompt() {
        Stage prompt = new Stage();
        prompt.setResizable(false);
        prompt.setTitle("New Group");

        TextField newGroupField = new TextField();
        newGroupField.setPromptText("New Group Name...");
        HBox centerContent = new HBox(newGroupField);

        Button addButton = new Button("Add Group");
        addButton.setOnAction(event -> { addNewLabel(newGroupField.getText()); prompt.close();});
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(event -> prompt.close());

        prompt.setScene(Utility.createDialogPane(null, centerContent, new ArrayList<>(Arrays.asList(addButton, cancelButton))));
        prompt.showAndWait();
    }
}
