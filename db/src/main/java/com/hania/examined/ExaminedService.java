package com.hania.examined;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class ExaminedService {

    private final Connection connection;

    private static final Logger LOG = LoggerFactory.getLogger(ExaminedService.class);

    public ExaminedService(Connection connection) {
        this.connection = connection;
    }

    public void add(Examined examined) {
        String sql = "INSERT INTO Examined(id, genotype, class) VALUES(null, ?, ?);";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, examined.getGenotype());
            ps.setString(2, examined.getClassification());
            ps.executeUpdate();
        } catch (SQLException e) {
            LOG.error("", e);
        }
    }

    public void update(Examined examined) {
        String sql = "UPDATE Examined SET genotype = ?, class = ? WHERE id = ?;";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, examined.getGenotype());
            ps.setString(2, examined.getClassification());
            ps.setInt(3, examined.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            LOG.error("", e);
        }
    }

    public void delete(Integer id) {
        String sql = "DELETE FROM Examined WHERE id = ?;";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            LOG.error("", e);
        }
    }

    public List<Examined> selectAll() {
        List<Examined> examinedList = new ArrayList<>();
        String query = "SELECT * FROM Examined;";
        try (Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(query)) {
            while (result.next()) {
                Integer id = result.getInt(1);
                String genotype = result.getString("genotype");
                String classification = result.getString("class");
                examinedList.add(new Examined(id, genotype, classification));
            }
        } catch (SQLException e) {
            LOG.error("", e);
        }
        return examinedList;
    }
}
