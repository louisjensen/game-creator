package ui.main;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import runner.external.Game;
import ui.AuthoringEntity;
import ui.AuthoringLevel;
import ui.ErrorBox;
import ui.LevelField;
import ui.Propertable;
import ui.PropertableType;
import ui.UIException;
import ui.manager.ObjectManager;
import ui.panes.DefaultTypesPane;
import ui.panes.PropertiesPane;
import ui.panes.UserCreatedTypesPane;
import ui.panes.Viewer;

/**
 * @author Harry Ross
 */
public class MainGUI {

    private Game myGame;
    private Stage myStage;
    private ObjectManager myObjectManager;
    private ObservableStringValue myCurrentStyle;
    private ObjectProperty<Propertable> mySelectedEntity;
    private ObjectProperty<Propertable> myCurrentLevel;

    private static final double STAGE_MIN_HEIGHT = 600;
    private static final double STAGE_MIN_WIDTH = 955;
    private static final double PROP_PANE_HEIGHT = 200;
    private static final String DEFAULT_STYLESHEET = "default.css"; //TODO propagate Stylesheet
    private static final String STAGE_TITLE = "ByteMe Authoring Environment";

    public MainGUI() { // Default constructor for creating a new game from scratch
        myGame = new Game();
        myStage = new Stage();
        myObjectManager = new ObjectManager();
        AuthoringLevel blankLevel = new AuthoringLevel("Level_1", myObjectManager);
        AuthoringEntity blankEntity = new AuthoringEntity("Object_1", myObjectManager);
        mySelectedEntity = new SimpleObjectProperty<>(blankEntity);
        myCurrentLevel = new SimpleObjectProperty<>(blankLevel);
        myCurrentStyle = new SimpleStringProperty(DEFAULT_STYLESHEET);
        myCurrentStyle.addListener((change, oldVal, newVal) -> swapStylesheet(oldVal, newVal));
    }

    public MainGUI(Game game) {
        this();
        myGame = game;
    }

    public void launch() {
        myStage.setTitle(STAGE_TITLE);
        myStage.setScene(createMainGUI());
        myStage.setMinHeight(STAGE_MIN_HEIGHT);
        myStage.show();
        myStage.setMinWidth(myStage.getWidth());
    }

    private Scene createMainGUI(){ //TODO split up
        BorderPane mainBorderPane = new BorderPane();
        Scene mainScene = new Scene(mainBorderPane);
        HBox propPaneBox = new HBox();
        propPaneBox.getStyleClass().add("prop-pane-box");
        HBox entityPaneBox = new HBox();
        entityPaneBox.getStyleClass().add("entity-pane-box");

        UserCreatedTypesPane userCreatedTypesPane = createTypePanes(entityPaneBox, mainScene);
        Viewer viewer = createViewer(userCreatedTypesPane);
        createPropertiesPanes(propPaneBox, mainScene);

        mainBorderPane.setCenter(viewer);
        mainBorderPane.setRight(entityPaneBox);
        mainBorderPane.setTop(addMenu());
        mainBorderPane.setBottom(propPaneBox);

        mainScene.getStylesheets().add(myCurrentStyle.getValue());
        return mainScene;
    }

    private UserCreatedTypesPane createTypePanes(HBox entityPaneBox, Scene mainScene) {
        UserCreatedTypesPane userCreatedTypesPane = new UserCreatedTypesPane(myObjectManager);
        DefaultTypesPane defaultTypesPane = new DefaultTypesPane(userCreatedTypesPane);
        entityPaneBox.getChildren().addAll(defaultTypesPane, userCreatedTypesPane);
        entityPaneBox.prefHeightProperty().bind(mainScene.heightProperty().subtract(PROP_PANE_HEIGHT));
        userCreatedTypesPane.prefHeightProperty().bind(entityPaneBox.heightProperty());
        defaultTypesPane.prefHeightProperty().bind(entityPaneBox.heightProperty());
        return userCreatedTypesPane;
    }

    private Viewer createViewer(UserCreatedTypesPane userCreatedTypesPane) {
        Viewer viewer = new Viewer(Integer.parseInt(myCurrentLevel.getValue().getPropertyMap().get(LevelField.WIDTH)),
                Integer.parseInt(myCurrentLevel.getValue().getPropertyMap().get(LevelField.HEIGHT)), userCreatedTypesPane, mySelectedEntity);
        viewer.setMinWidth(400);
        viewer.setMinHeight(300);
        return viewer;
    }

    private void createPropertiesPanes(HBox propPaneBox, Scene mainScene) {
        try {
            PropertiesPane objectProperties =
                    new PropertiesPane(PropertableType.OBJECT, mySelectedEntity, myObjectManager.getLabelManager());
            PropertiesPane levelProperties =
                    new PropertiesPane(PropertableType.LEVEL, myCurrentLevel, myObjectManager.getLabelManager());
            PropertiesPane instanceProperties =
                    new PropertiesPane(PropertableType.INSTANCE, mySelectedEntity, myObjectManager.getLabelManager());
            objectProperties.prefWidthProperty().bind(mainScene.widthProperty().divide(3));
            levelProperties.prefWidthProperty().bind(mainScene.widthProperty().divide(3));
            instanceProperties.prefWidthProperty().bind(mainScene.widthProperty().divide(3));
            propPaneBox.getChildren().addAll(levelProperties, instanceProperties, objectProperties);
        } catch (UIException e) {
            ErrorBox errorbox = new ErrorBox("Properties Error", e.getMessage());
            errorbox.display();
        }
    }

    private MenuBar addMenu() {
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        Menu editMenu = new Menu("Edit");
        Menu viewMenu = new Menu("View");
        menuBar.getMenus().addAll(fileMenu, editMenu, viewMenu);
        return menuBar;
    }

    private void swapStylesheet(String oldVal, String newVal) {
        myStage.getScene().getStylesheets().remove(oldVal);
        myStage.getScene().getStylesheets().add(newVal);
    }

}