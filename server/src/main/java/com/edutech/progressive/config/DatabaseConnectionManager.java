package com.edutech.progressive.config;

import java.io.InputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnectionManager {

    // Stores database configuration loaded from application.properties
    private static final Properties properties = new Properties();

    // Static block loads properties and JDBC driver once
    static {
        loadProperties();
        loadJdbcDriver();
    }

    // Loads database configuration from application.properties
    private static void loadProperties() {
        try (InputStream inputStream =
                     DatabaseConnectionManager.class
                             .getClassLoader()
                             .getResourceAsStream("application.properties")) {

            if (inputStream == null) {
                throw new RuntimeException(
                        "application.properties file not found in classpath");
            }

            properties.load(inputStream);

        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to load database properties", e);
        }
    }

    // Loads JDBC driver class from properties
    private static void loadJdbcDriver() {
        try {
            Class.forName(
                    properties.getProperty(
                            "spring.datasource.driver-class-name"));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("JDBC Driver class not found", e);
        }
    }

    // Returns a JDBC connection using loaded properties
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                properties.getProperty("spring.datasource.url"),
                properties.getProperty("spring.datasource.username"),
                properties.getProperty("spring.datasource.password")
        );
    }
}