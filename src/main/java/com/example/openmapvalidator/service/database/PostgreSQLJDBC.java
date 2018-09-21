package com.example.openmapvalidator.service.database;

import com.example.openmapvalidator.service.MapPlacesValidationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgreSQLJDBC {
    private static final Logger LOGGER = LoggerFactory.getLogger(PostgreSQLJDBC.class);

    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager
                        .getConnection("jdbc:postgresql://localhost:5432/map-db",
                                "postgres", "");
                connection.setAutoCommit(false);
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
            }
            LOGGER.info("Opened database successfully");
        }

        return connection;
    }
}