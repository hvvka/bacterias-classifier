package com.hania.toughness;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class ToughnessService {

    private static final Logger LOG = LoggerFactory.getLogger(ToughnessService.class);

    private final Connection connection;

    public ToughnessService(Connection connection) {
        this.connection = connection;
    }

    public void add(Toughness toughness) {
        String sql = "INSERT INTO Toughness(id, beta, gamma, rank) VALUES(null, ?, ?, ?);";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, toughness.getBeta());
            ps.setString(2, toughness.getGamma());
            ps.setString(3, toughness.getRank());
            ps.executeUpdate();
        } catch (SQLException e) {
            LOG.error("", e);
        }
    }

    public void update(Toughness toughness) {
        String sql = "UPDATE Toughness SET beta = ?, gamma = ?, rank = ? WHERE id = ?;";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, toughness.getBeta());
            ps.setString(2, toughness.getGamma());
            ps.setString(3, toughness.getRank());
            ps.setInt(4, toughness.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            LOG.error("", e);
        }
    }

    public void delete(Integer id) {
        String sql = "DELETE FROM Toughness WHERE id = ?;";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            LOG.error("", e);
        }
    }

    public List<Toughness> selectAll() {
        List<Toughness> toughnessList = new ArrayList<>();
        String query = "SELECT * FROM Toughness;";
        try (Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(query)) {
            while (result.next()) {
                Integer id = result.getInt(1);
                String beta = result.getString("beta");
                String gamma = result.getString("gamma");
                String rank = result.getString("rank");
                toughnessList.add(new Toughness(id, beta, gamma, rank));
            }
        } catch (SQLException e) {
            LOG.error("", e);
        }
        return toughnessList;
    }
}
