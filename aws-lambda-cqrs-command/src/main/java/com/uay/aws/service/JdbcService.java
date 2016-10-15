package com.uay.aws.service;


import com.amazonaws.services.lambda.runtime.LambdaLogger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcService {

    private static final String DB_HOST = "DB_HOST`";
    private static final String DB_PORT = "3306";
    private static final String DB_NAME = "command";
    private static final String DB_USERNAME = "DB_USERNAME";
    private static final String DB_PASSWORD = "DB_PASSWORD";

    private static final String INSERT_INTO_EVENTS = "INSERT INTO events(name, data, version) VALUES(?, ?, ?)";
    private static final String SELECT_LAST_VERSION_FROM_EVENTS = "SELECT max(version), data FROM events WHERE name=?";
    private static final String CONNECTION_URL = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME;

    private Connection connection = getDbConnection();

    public JdbcService() {
    }

    private Connection getDbConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(CONNECTION_URL, DB_USERNAME, DB_PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void persistEvent(LambdaLogger logger, String key, String data) throws SQLException {
        long eventVersion = getEventVersion(key);
        logger.log("Persisting version = " + eventVersion);
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_EVENTS);
        preparedStatement.setString(1, key);
        preparedStatement.setString(2, data);
        preparedStatement.setLong(3, eventVersion + 1);

        preparedStatement.execute();
        preparedStatement.close();
    }

    private long getEventVersion(String key) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_LAST_VERSION_FROM_EVENTS);
        preparedStatement.setString(1, key);

        long version = 0;
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            version = resultSet.getLong(1);
        }
        resultSet.close();
        preparedStatement.close();
        return version;
    }

    public String getPreviousEvent(String key) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_LAST_VERSION_FROM_EVENTS);
        preparedStatement.setString(1, key);

        String data = null;
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            data = resultSet.getString(2);
        }
        resultSet.close();
        preparedStatement.close();
        return data;
    }
}
