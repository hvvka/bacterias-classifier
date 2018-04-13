package com.hania;

import com.hania.examined.ExaminedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Objects;
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
        String rootPath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath();
        String databaseConfigPath = rootPath + "database.properties";
        Properties databaseProperties = new Properties();
        try {
            databaseProperties.load(new FileInputStream(databaseConfigPath));
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
//                connection.setAutoCommit(false);
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
    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                LOG.error("", e);
            }
        }
    }

    //todo delete
    public static void main(String[] args) {
        SqliteConnection sqliteConnection = new SqliteConnectionImpl();
        Connection connection = sqliteConnection.connect();

//        FlagellaService flagellaService = new FlagellaService(connection);
//        flagellaService.add(new Flagella(1, "12", "43", "1"));
//        flagellaService.add(new Flagella(1, "33", "24", "3"));
//        flagellaService.add(new Flagella(1, "34", "54", "2"));
//        flagellaService.add(new Flagella(1, "32", "43", "2"));
//
//        ToughnessService toughnessService = new ToughnessService(connection);
//        toughnessService.add(new Toughness(1, "43", "23", "d"));
//        toughnessService.add(new Toughness(1, "24", "43", "b"));
//        toughnessService.add(new Toughness(1, "54", "12", "b"));
//        toughnessService.add(new Toughness(1, "43", "43", "a"));
//
        ExaminedService examinedService = new ExaminedService(connection);
//        examinedService.add(new Examined(1, "328734", "1d"));
//        examinedService.add(new Examined(1, "653313", "3c"));
//        examinedService.add(new Examined(1, "239322", "1c"));
//        examinedService.add(new Examined(1, "853211", "2a"));
        LOG.info("{}", examinedService.selectAll().get(0).getId());
        sqliteConnection.disconnect();
    }
}
