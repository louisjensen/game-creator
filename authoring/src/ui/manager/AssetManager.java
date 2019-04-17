package ui.manager;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ui.*;
import ui.panes.AssetImageSubPane;

import java.io.File;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * @author Carrie Hunner
 * This Class creates an AssetManger Stage
 * It is a superclass designed to enable different assets
 * to be displayed. Currently its sub-classes are ImageManager and
 * Audio Manager. To add another subclass, the methods must be implemented
 * but the corresponding AssetManger properties file must be edited
 * to have a title for the pane and a list of acceptable file extensions
 */
abstract public class AssetManager extends Stage {
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle("asset_manager");
    protected static final ResourceBundle GENERAL_RESOURCES = ResourceBundle.getBundle("authoring_general");
    private Set<String> myImageExtensions;
    private ScrollPane myImageScrollPane;
    private HBox myButtonHBox;
    protected String mySelectedAssetName;
    private TitledPane myImageTitledPane;
    private VBox myOuterVBox;
    private static final String BUTTON_INFO = "Buttons";

    private static final String IO_ERROR = "IOError";
    private static final String ERROR_HEADER = "ErrorHeader";
    private static final String EXTENSION_PREFIX = "*.";
    private static final String DEFAULT_STYLE_SHEET = "default.css";
    private static final String ASSET_SPECIFIC_SHEET = "asset-manager";
    protected String myAssetFolderPath;
    protected String myTitleKey;
    protected String myExtensionKey;
    private static final double SPACING = 10;
    private static final int STAGE_WIDTH = 400;
    private static final int STAGE_HEIGHT = 300;
    private static final int MAX_NUM_COLS = 4;
    private static final int IMAGE_SUBPANE_SIZE = 60;
    private static final int BUTTON_SPACING = 20;
    private static final Insets INSETS = new Insets(SPACING, SPACING, SPACING, SPACING);

    /**
     * This is a constructor that forces the coder to input info for creating the window
     * @param assetFolderPath String of the path to the assetFolder
     * @param titleKey String of the key to the window title in the asset_manager properties file
     * @param extensionKey String of the key to the list of extensions in the asset_manager properties file
     */
    public AssetManager(String assetFolderPath, String titleKey, String extensionKey){
        myAssetFolderPath = assetFolderPath;
        myTitleKey = titleKey;
        myExtensionKey = extensionKey;
        mySelectedAssetName = "";
        initializeVariables();
        initializeStage();
        fillImageExtensionSet();
        drawImageScrollPane();
        createButtonPane();
        fillVBox();
    }



    private void fillVBox() {
        myOuterVBox.getChildren().add(myImageTitledPane);
        myOuterVBox.getChildren().add(myButtonHBox);
    }


    private void createButtonPane() {
        String buttonString = RESOURCES.getString(BUTTON_INFO);
        String[] buttonInfo = buttonString.split(",");
        formatButtonHBox();
        for(String s : buttonInfo){
            String[] textAndMethod = s.split(" ");
            Button button = Utility.makeButton(this, textAndMethod[1], textAndMethod[0]);
            myButtonHBox.getChildren().add(button);
        }

    }

    private void formatButtonHBox() {
        myButtonHBox.setFillHeight(true);
        myButtonHBox.setSpacing(BUTTON_SPACING);
        myButtonHBox.setAlignment(Pos.CENTER);
    }

    private void drawImageScrollPane() {
        GridPane gridPane = createAndFormatGridPane();
        myImageScrollPane.setPadding(INSETS);
        myImageScrollPane.setContent(gridPane);
        File assetFolder = new File(myAssetFolderPath);
        int row = 0;
        int col = 0;
        for(File temp : assetFolder.listFiles()){
            try {
                String lowerCaseExtension = temp.getName().split("\\.")[1].toLowerCase();
                if(myImageExtensions.contains(lowerCaseExtension)){
                    Pane subPane = createSubPane(temp);
                    if(col > MAX_NUM_COLS){
                        col = 0;
                        row++;
                    }
                    gridPane.add(subPane, col, row);
                    col++;
                }
            }
            catch (IndexOutOfBoundsException e){
                //this occurs when there is no file extension
                //this file should be skipped over and nothing should happen
                //this catch should be empty
            }
        }
    }


    abstract protected AssetImageSubPane createSubPane(File temp);

    private GridPane createAndFormatGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(INSETS);
        ColumnConstraints colRestraint = new ColumnConstraints(SPACING + IMAGE_SUBPANE_SIZE);
        gridPane.getColumnConstraints().add(colRestraint);
        RowConstraints rowRestraint = new RowConstraints(SPACING + IMAGE_SUBPANE_SIZE);
        gridPane.getRowConstraints().add(rowRestraint);
        gridPane.setAlignment(Pos.CENTER);
        return gridPane;
    }



    private void initializeVariables(){
        myImageExtensions = new HashSet<>();
        myOuterVBox = new VBox();
        myOuterVBox.setPrefHeight(STAGE_HEIGHT);
        myImageScrollPane = new ScrollPane();
        myButtonHBox = new HBox();
        myImageTitledPane = new TitledPane();
        myImageTitledPane.setContent(myImageScrollPane);
        myImageTitledPane.setText(RESOURCES.getString(myTitleKey));
    }

    private void initializeStage(){
        this.setResizable(false);
        this.setWidth(STAGE_WIDTH);
        this.setHeight(STAGE_HEIGHT);
        Scene scene = new Scene(myOuterVBox);
        scene.getStylesheets().add(DEFAULT_STYLE_SHEET);
        myImageTitledPane.getStyleClass().add(ASSET_SPECIFIC_SHEET);
        this.setScene(scene);
    }

    private void fillImageExtensionSet() {
        for(String s: RESOURCES.getString(myExtensionKey).split(",")){
            myImageExtensions.add(s);
        }
    }

    private void handleBrowse(){
        Stage stage = new Stage();
        FileChooser chooser = new FileChooser();
        initializeFileExtensions(chooser);
        File selectedFile = chooser.showOpenDialog(stage);
        if(selectedFile != null){
            saveAsset(selectedFile);
            drawImageScrollPane();
        }
    }

    abstract protected void saveAsset(File selectedFile);

    private void initializeFileExtensions(FileChooser chooser) {
        for(String s : myImageExtensions){
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(s,EXTENSION_PREFIX + s);
            chooser.getExtensionFilters().add(extFilter);
        }
    }

    private void createAndDisplayAlert(String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(RESOURCES.getString(ERROR_HEADER));
        alert.setHeaderText(contentText);
        alert.setContentText(null);
        alert.show();
    }

    protected void handleClose(){
        mySelectedAssetName = null;
        this.close();
    }

    /**
     * this method is called when an exception occurs in trying to save a file uploaded
     * by the user
     * It displays an error box
     */
    protected void handleSavingException(){
        String[] text = RESOURCES.getString(IO_ERROR).split(",");
        ErrorBox errorBox = new ErrorBox(text[0], text[1]);
        errorBox.display();
    }

    /**
     * This method is called by subclasses when someone hits the "Apply" button but
     * no Asset is selected
     */
    protected void handleNoAssetSelected(){
        String[] text = RESOURCES.getString("NOIMAGE").split(",");
        ErrorBox errorBox = new ErrorBox(text[0], text[1]);
        errorBox.display();
    }

    abstract protected void handleApply();


    /**
     * Gets the Name of the selected asset
     * @return String of the selected asset name
     */
    public String getAssetName(){
        return mySelectedAssetName;
    }
}
