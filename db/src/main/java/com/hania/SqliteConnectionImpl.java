package com.hania;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class SqliteConnectionImpl implements SqliteConnection {

    private static final Logger LOG = LoggerFactory.getLogger(SqliteConnectionImpl.class);

    private String databaseUrl;

    private Connection connection;

    public SqliteConnectionImpl() {
        getDatabaseUrl();
    }

    public SqliteConnectionImpl(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    private void getDatabaseUrl() {
        Properties databaseProperties = new Properties();
        try (InputStream stream = this.getClass().getClassLoader().getResourceAsStream("database.properties")) {
            databaseProperties.load(stream);
            String databaseDriver = databaseProperties.getProperty("database.driver");
            String databasePath = databaseProperties.getProperty("database.path");
            databaseUrl = databaseDriver + databasePath;
        } catch (IOException e) {
            LOG.error("", e);
        }
    }

    @Override
    public Connection connect() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(databaseUrl);
                LOG.info("Connection to SQLite has been established.");
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                LOG.error("", e);
            }
        }
        return connection;
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
            LOG.info("Connection closed.");
            connection = null;
        }
    }
}
