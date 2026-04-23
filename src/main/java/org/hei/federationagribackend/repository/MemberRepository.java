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

    public MemberEntity save(MemberEntity m, String collectivityId) {

        String sql = """
            INSERT INTO member (
                id,
                first_name,
                last_name,
                birth_date,
                gender,
                address,
                profession,
                phone_number,
                email,
                occupation,
                registration_fee_paid,
                membership_dues_paid,
                collectivity_id
            )
            VALUES (
                ?, ?, ?, ?, 
                ?::gender,
                ?, ?, ?, ?, 
                ?::member_occupation,
                ?, ?, ?
            )
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, m.getId());
            ps.setString(2, m.getFirstName());
            ps.setString(3, m.getLastName());
            ps.setDate(4, Date.valueOf(m.getBirthDate()));

            ps.setString(5, m.getGender().name());

            ps.setString(6, m.getAddress());
            ps.setString(7, m.getProfession());

            ps.setString(8, String.valueOf(m.getPhoneNumber()));

            ps.setString(9, m.getEmail());

            ps.setString(10, m.getOccupation().name());

            ps.setBoolean(11, m.getRegistrationFeePaid());
            ps.setBoolean(12, m.getMembershipDuesPaid());

            ps.setString(13, collectivityId);

            ps.executeUpdate();

            return m;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public MemberEntity findById(String id) {

        String sql = "SELECT     id,\n" +
                "                first_name,\n" +
                "                last_name,\n" +
                "                birth_date,\n" +
                "                gender,\n" +
                "                address,\n" +
                "                profession,\n" +
                "                phone_number,\n" +
                "                email,\n" +
                "                occupation,\n" +
                "                registration_fee_paid,\n" +
                "                membership_dues_paid FROM member WHERE id = ?";

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
            m.setPhoneNumber(Integer.valueOf(rs.getString("phone_number")));
            m.setEmail(rs.getString("email"));

            m.setOccupation(MemberOccupation.valueOf(rs.getString("occupation")));

            m.setRegistrationFeePaid(rs.getBoolean("registration_fee_paid"));
            m.setMembershipDuesPaid(rs.getBoolean("membership_dues_paid"));

            return m;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}