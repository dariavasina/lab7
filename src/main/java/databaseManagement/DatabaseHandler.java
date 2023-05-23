package databaseManagement;

import common.collectionClasses.*;
import common.exceptions.InvalidInputException;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class DatabaseHandler {
    private String url;
    private String username;
    private String password;
    private Connection connection;

    public DatabaseHandler(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public void connectToDatabase() {
        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to database");
        } catch (SQLException e) {
            System.out.println("Connection to database was unsuccessful, please check your credentials");
            System.exit(-1);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public synchronized boolean userExists(String username) throws SQLException {
        String checkUsersExists = "select " +
                "exists (" +
                " select username" +
                " from users" +
                " where username = ?);";

        try (PreparedStatement userExistsStatement = connection.prepareStatement(checkUsersExists)) {
            userExistsStatement.setString(1, username);

            ResultSet resultSet = userExistsStatement.executeQuery();

            return resultSet.next() && resultSet.getBoolean(1);
        }
    }

    /**
     * Method that adds a new user to database
     */
    public synchronized boolean registerUser(String username, String password) throws SQLException {

        if (userExists(username)) {
            return false;
        }

        String addUserQuery = "insert into users (username, password) values (?, ?)";

        try (PreparedStatement registerUserStatement = connection.prepareStatement(addUserQuery)) {

            registerUserStatement.setString(1, username);
            registerUserStatement.setString(2, password);

            registerUserStatement.executeUpdate();
        }

        return true;
    }

    public synchronized String getUsersPassword(String username) throws SQLException {

        String getPasswordQuery = "select password from users where username = ?";

        try (PreparedStatement getPasswordStatement = connection.prepareStatement(getPasswordQuery)) {

            getPasswordStatement.setString(1, username);

            ResultSet resultSet = getPasswordStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString(1);
            } else {
                return null;
            }
        }
    }

}
