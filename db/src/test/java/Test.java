import com.hania.SqliteConnection;
import com.hania.SqliteConnectionImpl;
import com.hania.examined.Examined;
import com.hania.examined.ExaminedService;
import com.hania.flagella.Flagella;
import com.hania.flagella.FlagellaService;
import com.hania.toughness.Toughness;
import com.hania.toughness.ToughnessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class Test {

    private static final Logger LOG = LoggerFactory.getLogger(Test.class);

    //todo delete
    public static void main(String[] args) {
        try (SqliteConnection sqliteConnection = new SqliteConnectionImpl()) {
            Connection connection = sqliteConnection.connect();
            ExaminedService examinedService = new ExaminedService(connection);
//            populateDatabase(connection);
            LOG.info("{}", examinedService.selectAll().get(0).getId());
        } catch (SQLException e) {
            LOG.error("", e);
        }
    }

    private static void populateDatabase(Connection connection) {
        FlagellaService flagellaService = new FlagellaService(connection);
        flagellaService.add(new Flagella(1, "12", "43", "1"));
        flagellaService.add(new Flagella(1, "33", "24", "3"));
        flagellaService.add(new Flagella(1, "34", "54", "2"));
        flagellaService.add(new Flagella(1, "32", "43", "2"));

        ToughnessService toughnessService = new ToughnessService(connection);
        toughnessService.add(new Toughness(1, "43", "23", "d"));
        toughnessService.add(new Toughness(1, "24", "43", "b"));
        toughnessService.add(new Toughness(1, "54", "12", "b"));
        toughnessService.add(new Toughness(1, "43", "43", "a"));

        ExaminedService examinedService = new ExaminedService(connection);
        examinedService.add(new Examined(1, "328734", "1d"));
        examinedService.add(new Examined(1, "653313", "3c"));
        examinedService.add(new Examined(1, "239322", "1c"));
        examinedService.add(new Examined(1, "853211", "2a"));
    }
}
