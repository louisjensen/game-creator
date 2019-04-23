package data.internal;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserQuerier extends Querier{

    public static final String USERS_TABLE_NAME= "Users";
    public static final String USERNAME_COLUMN = "UserName";
    private static final String PASSWORD_COLUMN = "Password";
    private static final String AUTHORED_GAMES_COLUMN = "AuthoredGames";
    private static final String LAST_LOGIN_COLUMN = "LastLogin";
    private static final String FIRST_LOGIN_COLUMN = "FirstLogin";

    private static final String GET_HASHED_PASSWORD = "SELECT " + PASSWORD_COLUMN + " FROM " + USERS_TABLE_NAME + " " +
            "WHERE " + USERNAME_COLUMN + " = ?";
    private static final String CREATE_USER =
            "INSERT INTO " + USERS_TABLE_NAME + " (" + USERNAME_COLUMN + ", " + PASSWORD_COLUMN + ") VALUES (?, ?)";

    private static final String HASH_ALGORITHM = "SHA-256";

    private PreparedStatement myGetPasswordStatement;
    private PreparedStatement myCreateUserStatement;

    public UserQuerier(Connection connection) throws SQLException{
        super(connection);
    }

    @Override
    protected void prepareStatements() throws SQLException {
        myGetPasswordStatement = myConnection.prepareStatement(GET_HASHED_PASSWORD);
        myCreateUserStatement = myConnection.prepareStatement(CREATE_USER);
    }

    @Override
    protected void closeStatements() {

    }

    public boolean authenticateUser(String userName, String password) {
        try {
            String hashedPassword = generateHashedPassword(password);
            String storedHash = retrieveStoredHash(userName);
            return hashedPassword.equals(storedHash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Could not validate user: " + e.getMessage());
        }
        return false;
    }

    public boolean createUser(String userName, String password) throws SQLException{
        myCreateUserStatement.setString(1, userName);
        try {
            myCreateUserStatement.setString(2, generateHashedPassword(password));
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Could not generate hash: " + e.getMessage());
        }
        int updates = myCreateUserStatement.executeUpdate();
        return updates == 1;
    }

    private String retrieveStoredHash(String userName) throws SQLException{
        myGetPasswordStatement.setString(1, userName);
        ResultSet resultSet = myGetPasswordStatement.executeQuery();
        if (resultSet.next()){
            return resultSet.getString(PASSWORD_COLUMN);
        } else {
            return "";
        }
    }

    private String generateHashedPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(HASH_ALGORITHM);
        byte[] bytes = messageDigest.digest(password.getBytes());
        StringBuilder stringBuilder = new StringBuilder();
        for (byte bite : bytes) {
            stringBuilder.append(Integer.toString((bite & 0xff) + 0x100, 16).substring(1));
        }
        return stringBuilder.toString();
    }
}
