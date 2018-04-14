package com.hania;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public interface SqliteConnection extends AutoCloseable  {

    Connection connect();

    Connection getConnection();

    void close() throws SQLException;
}
