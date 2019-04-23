import data.external.DatabaseEngine;
import data.internal.UserQuerier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DataTest {

    private String userName1;
    private String userName2;
    private String correctPassword;
    private String incorrectPassword;
    private String fakeGameName1;
    private String fakeGameName2;
    private String fakeGameData1;
    private String fakeGameData2;
    private String fakeGameInfo1;
    private String fakeGameInfo2;

    @BeforeEach
    protected void setUp(){
        instantiateVariables();
        clearDatabase();

    }

    private void clearDatabase() {
        DatabaseEngine.getInstance().open();

    }

    private void instantiateVariables() {
        userName1 = "userName1";
        userName2 = "userName2";
        correctPassword = "correctPassword";
        incorrectPassword = "incorrectPassword";
        fakeGameName1 = "fakeGameName1";
        fakeGameName2 = "fakeGameName2";
        fakeGameData1 = "fakeGameData1";
        fakeGameData2 = "fakeGameData2";
        fakeGameInfo1 = "fakeGameInfo1";
        fakeGameInfo2 = "fakeGameInfo2";
    }

    @Test
    public void testCreateUser(){

    }
}
