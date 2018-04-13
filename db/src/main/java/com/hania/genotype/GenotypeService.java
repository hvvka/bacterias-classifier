package com.hania.genotype;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class GenotypeService {

    private static final Logger LOG = LoggerFactory.getLogger(GenotypeService.class);

    private final Connection connection;

    public GenotypeService(Connection connection) {
        this.connection = connection;
    }

    public List<Genotype> selectAll() {
        List<Genotype> genotypeList = new ArrayList<>();
        String query = "SELECT * FROM Flagella JOIN Toughness ON beta;";
        try (Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(query)) {
            while (result.next()) {
                String alfa = result.getString("alfa");
                String beta = result.getString("beta");
                String gamma = result.getString("gamma");
                genotypeList.add(
                        new Genotype(Integer.valueOf(alfa), Integer.valueOf(beta), Integer.valueOf(gamma))
                );
            }
        } catch (SQLException e) {
            LOG.error("", e);
        }
        return genotypeList;
    }
}
