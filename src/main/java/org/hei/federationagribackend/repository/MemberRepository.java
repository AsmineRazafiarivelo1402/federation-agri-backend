package org.hei.federationagribackend.repository;

import org.hei.federationagribackend.entity.Gender;
import org.hei.federationagribackend.entity.MemberEntity;
import org.hei.federationagribackend.entity.MemberOccupation;
import org.hei.federationagribackend.exception.NotFoundException;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class MemberRepository {

    private final Connection connection;

    public MemberRepository(Connection connection) {
        this.connection = connection;
    }

    public MemberEntity save(MemberEntity m) {
        String sql = """
            INSERT INTO member (
                first_name, last_name, birth_date, gender,
                address, profession, phone_number, email,
                occupation, registration_fee_paid, membership_dues_paid
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, m.getFirstName());
            ps.setString(2, m.getLastName());
            ps.setDate(3, Date.valueOf(m.getBirthDate()));
            ps.setString(4, m.getGender().name());
            ps.setString(5, m.getAddress());
            ps.setString(6, m.getProfession());
            ps.setInt(7, m.getPhoneNumber());
            ps.setString(8, m.getEmail());
            ps.setString(9, m.getOccupation().name());
            ps.setBoolean(10, m.getRegistrationFeePaid());
            ps.setBoolean(11, m.getMembershipDuesPaid());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                m.setId(rs.getString(1));
            }

            return m;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public MemberEntity findById(String id) {
        String sql = "SELECT * FROM member WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, id);

            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                throw new NotFoundException("Member not found: " + id);
            }

            MemberEntity m = new MemberEntity();
            m.setId(rs.getString("id"));
            m.setFirstName(rs.getString("first_name"));
            m.setLastName(rs.getString("last_name"));
            m.setBirthDate(rs.getDate("birth_date").toLocalDate());
            m.setGender(Gender.valueOf(rs.getString("gender")));
            m.setAddress(rs.getString("address"));
            m.setProfession(rs.getString("profession"));
            m.setPhoneNumber(rs.getInt("phone_number"));
            m.setEmail(rs.getString("email"));
            m.setOccupation(MemberOccupation.valueOf(rs.getString("occupation")));

            return m;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}