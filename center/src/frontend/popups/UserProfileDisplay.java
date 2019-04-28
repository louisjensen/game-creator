package frontend.popups;

import data.external.DataManager;
import data.external.GameCenterData;
import frontend.Utilities;
import frontend.games.GameList;
import javafx.stage.Stage;

import java.sql.SQLException;

public class UserProfileDisplay extends Popup {
    private String myUsername;
    private String myCurrentUser;
    private GameCenterData myData;

    private static final double USER_WIDTH = 750;
    private static final double USER_HEIGHT = 500;

    public UserProfileDisplay(GameCenterData data, DataManager manager, String currentUser, String username) {
        super(manager);
        myData = data;
        myUsername = username;
        myCurrentUser = currentUser;
        initializeDisplay();
        display();
    }

    public void closeButton(GameCenterData data) {
        ((Stage) myDisplay.getScene().getWindow()).close();
    }

    @Override
    protected void addHeader() {
        try {
            addTitleAndSubtitle(myDisplay, myUsername, myManager.getBio(myUsername));
        } catch (SQLException e) {
            System.out.println("yeah sql exception ya dunce");
            //todo: handle this
        }
    }

    @Override
    protected void addBody() {
        myDisplay.setCenter(new GameList(myCurrentUser, myUsername).getDisplay());
    }

    @Override
    protected void addButtons() {
        myDisplay.setBottom(Utilities.makeButtons(this, myData));
    }

    @Override
    protected void display() {
        showScene(myDisplay, USER_WIDTH, USER_HEIGHT);
    }
}
