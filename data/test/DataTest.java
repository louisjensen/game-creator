import data.external.DataManager;
import data.external.DatabaseEngine;
import data.internal.UserQuerier;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Suite of tests for the data module that show the basics of how the module works
 */
public class DataTest {

    private String myUserName1;
    private String myUserName2;
    private String myCorrectPassword;
    private String myIncorrectPassword;
    private String myFakeGameName1;
    private String myFakeGameName2;
    private String myFakeGameData1;
    private DataManager myDataManager;

    @BeforeEach
    protected void setUp(){
        // Must open the connection to the database before it can be used
        // DatabaseEngine uses the singleton design pattern
        DatabaseEngine.getInstance().open();
        myDataManager = new DataManager();
        instantiateVariables();
        clearDatabase();
    }

    @AfterEach
    protected void closeResources() {
        clearDatabase();
        DatabaseEngine.getInstance().close();
    }

    private void clearDatabase() {
        try {
            myDataManager.removeGame(myFakeGameName1, myUserName1);
            myDataManager.removeGame(myFakeGameName1, myUserName2);
            myDataManager.removeGame(myFakeGameName2, myUserName1);
            myDataManager.removeGame(myFakeGameName2, myUserName2);
            myDataManager.removeUser(myUserName1);
            myDataManager.removeUser(myUserName2);
        } catch (SQLException exception) {
            // just debugging the test cases, does not get included
            exception.printStackTrace();
        }

    }

    private void instantiateVariables() {
        myUserName1 = "userName1";
        myUserName2 = "userName2";
        myCorrectPassword = "correctPassword";
        myIncorrectPassword = "incorrectPassword";
        myFakeGameName1 = "fakeGameName1";
        myFakeGameName2 = "fakeGameName2";
        myFakeGameData1 = "fakeGameData1";
    }

    @Test
    public void testCreateUser(){
        // Can only create one user for each user name
        assertTrue(myDataManager.createUser(myUserName1, myCorrectPassword));
        assertFalse(myDataManager.createUser(myUserName1, myIncorrectPassword));
    }

    @Test
    public void testValidateUser() {
        // User is only validated if they enter the correct username and password
        assertTrue(myDataManager.createUser(myUserName1, myCorrectPassword));
        assertTrue(myDataManager.validateUser(myUserName1, myCorrectPassword));
        assertFalse(myDataManager.validateUser(myUserName1, myIncorrectPassword));
        assertFalse(myDataManager.validateUser(myUserName2, myCorrectPassword));
    }

    @Test
    public void testSaveAndLoadGameData() {
        // Data can save any object as "game data" and reload it as long as it is casted properly when loaded
        myDataManager.saveGameData(myFakeGameName1, myUserName1, myFakeGameData1);
        String loadedData = (String) myDataManager.loadGameData(myFakeGameName1);
        assertEquals(loadedData, myFakeGameData1);
    }

    @Test
    public void testInvalidConnection() {
        // If the connection is closed, SQLExceptions will be thrown
        DatabaseEngine.getInstance().close();
        assertThrows(SQLException.class, () -> myDataManager.removeUser(myUserName1));
        DatabaseEngine.getInstance().open();
    }
}
