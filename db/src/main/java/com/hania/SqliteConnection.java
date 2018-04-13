package com.hania;

import java.sql.Connection;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public interface SqliteConnection {

    Connection connect();

    Connection getConnection();

    void disconnect();
}
