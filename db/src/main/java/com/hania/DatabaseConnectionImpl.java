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
public class DatabaseConnectionImpl implements DatabaseConnection {

    private static final Logger LOG = LoggerFactory.getLogger(DatabaseConnectionImpl.class);

    private String databaseDriver;
    private String databaseUrl;
    private String username;
    private String password;
    private String maxPool;

    private Connection connection;

    private Properties properties;

    public DatabaseConnectionImpl() {
        getDatabaseProperties();
        getDatabaseDriverAndUrl();
    }

    public DatabaseConnectionImpl(String databaseDriver, String databaseUrl) {
        getDatabaseProperties();
        this.databaseDriver = databaseDriver;
        this.databaseUrl = databaseUrl;
    }

    private void getDatabaseDriverAndUrl() {
        Properties databaseProperties = new Properties();
        try (InputStream stream = this.getClass().getClassLoader().getResourceAsStream("database.properties")) {
            databaseProperties.load(stream);
            databaseDriver = databaseProperties.getProperty("database.driver");
            databaseUrl = databaseProperties.getProperty("database.url");
        } catch (IOException e) {
            LOG.error("", e);
        }
    }

    private void getDatabaseProperties() {
        Properties databaseProperties = new Properties();
        try (InputStream stream = this.getClass().getClassLoader().getResourceAsStream("database.properties")) {
            databaseProperties.load(stream);
            username = databaseProperties.getProperty("database.username");
            password = databaseProperties.getProperty("database.password");
            maxPool = databaseProperties.getProperty("database.max-pool");
        } catch (IOException e) {
            LOG.error("", e);
        }
    }

    @Override
    public Connection connect() {
        if (connection == null) {
            try {
                Class.forName(databaseDriver);
                connection = DriverManager.getConnection(databaseUrl, getProperties());
                LOG.info("Connection to database has been established.");
                connection.setAutoCommit(false);
            } catch (SQLException | ClassNotFoundException e) {
                LOG.error("", e);
            }
        }
        return connection;
    }

    private Properties getProperties() {
        if (properties == null) {
            properties = new Properties();
            properties.setProperty("user", username);
            properties.setProperty("password", password);
            properties.setProperty("MaxPooledStatements", maxPool);
        }
        return properties;
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
