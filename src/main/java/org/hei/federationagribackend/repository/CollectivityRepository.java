package org.hei.federationagribackend.repository;

import org.hei.federationagribackend.entity.CollectivityEntity;
import org.hei.federationagribackend.entity.CollectivityStructure;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class CollectivityRepository {

    private final Connection connection;

    public CollectivityRepository(Connection connection) {
        this.connection = connection;
    }

    public CollectivityEntity save(CollectivityEntity c) {

        String sql = """
            INSERT INTO collectivity (id, location, federation_approval)
            VALUES (?, ?, ?)
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, c.getId());
            ps.setString(2, c.getLocation());
            ps.setBoolean(3, c.getFederationApproval());

            ps.executeUpdate();

            return c;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveStructure(String collectivityId, CollectivityStructure s) {

        String sql = """
            INSERT INTO collectivity_structure (
                collectivity_id,
                president_id,
                vice_president_id,
                treasurer_id,
                secretary_id
            )
            VALUES (?, ?, ?, ?, ?)
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, collectivityId);
            ps.setString(2, s.getPresidentId());
            ps.setString(3, s.getVicePresidentId());
            ps.setString(4, s.getTreasurerId());
            ps.setString(5, s.getSecretaryId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveMemberRelation(String collectivityId, String memberId) {

        String sql = """
            UPDATE member SET collectivity_id = ?
            WHERE id = ?
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, collectivityId);
            ps.setString(2, memberId);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public CollectivityEntity findById(String id) {
        String sql = "SELECT * FROM collectivity WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) return null;

            CollectivityEntity c = new CollectivityEntity();
            c.setId(rs.getString("id"));
            c.setLocation(rs.getString("location"));
            c.setFederationApproval(rs.getBoolean("federation_approval"));

            c.setName(rs.getString("name"));
            c.setNumber(rs.getString("registration_number"));

            return c;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean existsByName(String name) {
        String sql = "SELECT 1 FROM collectivity WHERE name = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            return ps.executeQuery().next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean existsByNumber(String number) {
        String sql = "SELECT 1 FROM collectivity WHERE registration_number = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, number);
            return ps.executeQuery().next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateIdentity(String id, String name, String number) {

        String sql = """
        UPDATE collectivity
        SET name = ?, registration_number = ?
        WHERE id = ?
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, number);
            ps.setString(3, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
