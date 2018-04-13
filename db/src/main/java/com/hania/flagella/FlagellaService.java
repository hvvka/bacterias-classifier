package com.hania.flagella;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class FlagellaService {

    private static final Logger LOG = LoggerFactory.getLogger(FlagellaService.class);

    private final Connection connection;

    public FlagellaService(Connection connection) {
        this.connection = connection;
    }

    public void add(Flagella flagella) {
        String sql = "INSERT INTO Flagella(id, alfa, beta, number) VALUES(null, ?, ?, ?);";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, flagella.getAlfa());
            ps.setString(2, flagella.getBeta());
            ps.setString(3, flagella.getNumber());
            ps.executeUpdate();
        } catch (SQLException e) {
            LOG.error("", e);
        }
    }

    public void update(Flagella flagella) {
        String sql = "UPDATE Flagella SET alfa = ?, beta = ?, number = ? WHERE id = ?;";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, flagella.getAlfa());
            ps.setString(2, flagella.getBeta());
            ps.setString(3, flagella.getNumber());
            ps.setInt(4, flagella.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            LOG.error("", e);
        }
    }

    public void delete(Integer id) {
        String sql = "DELETE FROM Flagella WHERE id = ?;";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            LOG.error("", e);
        }
    }

    public List<Flagella> selectAll() {
        List<Flagella> flagellaList = new ArrayList<>();
        String query = "SELECT * FROM Flagella;";
        try (Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(query)) {
            while (result.next()) {
                Integer id = result.getInt(1);
                String alfa = result.getString("alfa");
                String beta = result.getString("beta");
                String number = result.getString("number");
                flagellaList.add(new Flagella(id, alfa, beta, number));
            }
        } catch (SQLException e) {
            LOG.error("", e);
        }
        return flagellaList;
    }
}
