import data.external.DataManager;
import data.external.DatabaseEngine;
import data.internal.UserQuerier;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

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
        assertTrue(myDataManager.createUser(myUserName1, myCorrectPassword));
        assertFalse(myDataManager.createUser(myUserName1, myIncorrectPassword));
    }

    @Test
    public void testValidateUser() {
        assertTrue(myDataManager.createUser(myUserName1, myCorrectPassword));
        assertTrue(myDataManager.validateUser(myUserName1, myCorrectPassword));
        assertFalse(myDataManager.validateUser(myUserName1, myIncorrectPassword));
        assertFalse(myDataManager.validateUser(myUserName2, myCorrectPassword));
    }

    @Test
    public void testSaveAndLoadGameData() {
        myDataManager.saveGameData(myFakeGameName1, myUserName1, myFakeGameData1);
        String loadedData = (String) myDataManager.loadGameData(myFakeGameName1);
        assertEquals(loadedData, myFakeGameData1);
    }

    @Test
    public void testInvalidConnection() {
        DatabaseEngine.getInstance().close();
        assertThrows(SQLException.class, () -> myDataManager.removeUser(myUserName1));
        DatabaseEngine.getInstance().open();
    }
}
