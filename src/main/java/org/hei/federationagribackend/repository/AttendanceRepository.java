package org.hei.federationagribackend.repository;

import org.hei.federationagribackend.dto.ActivityMemberAttendanceDTO;
import org.hei.federationagribackend.dto.MemberDescriptionDTO;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AttendanceRepository {

    private final Connection connection;

    public AttendanceRepository(Connection connection) {
        this.connection = connection;
    }


    public boolean existsActivityById(String activityId) {
        String sql = "SELECT id FROM activity WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, activityId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String findAttendanceStatus(String activityId, String memberId) {
        String sql = "SELECT attendance_status FROM activity_attendance WHERE activity_id = ? AND member_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, activityId);
            ps.setString(2, memberId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("attendance_status");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void update(String activityId, String memberId, String attendanceStatus) {
        String sql = "UPDATE activity_attendance SET attendance_status = ?::attendance_status_enum WHERE activity_id = ? AND member_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, attendanceStatus);
            ps.setString(2, activityId);
            ps.setString(3, memberId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(String id, String activityId, String memberId, String attendanceStatus) {
        String sql = "INSERT INTO activity_attendance (id, activity_id, member_id, attendance_status) VALUES (?, ?, ?, ?::attendance_status_enum)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setString(2, activityId);
            ps.setString(3, memberId);
            ps.setString(4, attendanceStatus);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ActivityMemberAttendanceDTO> findAllByActivityId(String activityId) {
        List<ActivityMemberAttendanceDTO> result = new ArrayList<>();
        String sql = "SELECT aa.id, aa.member_id, aa.attendance_status, " +
                "m.first_name, m.last_name, m.email, m.occupation " +
                "FROM activity_attendance aa " +
                "JOIN member m ON aa.member_id = m.id " +
                "WHERE aa.activity_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, activityId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ActivityMemberAttendanceDTO dto = new ActivityMemberAttendanceDTO();
                    dto.setId(rs.getString("id"));
                    dto.setAttendanceStatus(rs.getString("attendance_status"));

                    MemberDescriptionDTO memberDesc = new MemberDescriptionDTO();
                    memberDesc.setId(rs.getString("member_id"));
                    memberDesc.setFirstName(rs.getString("first_name"));
                    memberDesc.setLastName(rs.getString("last_name"));
                    memberDesc.setEmail(rs.getString("email"));
                    memberDesc.setOccupation(rs.getString("occupation"));
                    dto.setMemberDescription(memberDesc);

                    result.add(dto);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public Double calculateAssiduityPercentage(String memberId, String collectivityId, LocalDate from, LocalDate to) {
        String sql = """
        SELECT 
            COUNT(CASE WHEN aa.attendance_status = 'ATTENDED' THEN 1 END) AS attended_count,
            COUNT(*) AS total_activities
        FROM activity_attendance aa 
        JOIN activity a ON aa.activity_id = a.id 
        WHERE aa.member_id = ? 
          AND a.collectivity_id = ? 
          AND a.executive_date BETWEEN ? AND ?
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, memberId);
            ps.setString(2, collectivityId);
            ps.setObject(3, from);
            ps.setObject(4, to);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    long attended = rs.getLong("attended_count");
                    long total = rs.getLong("total_activities");

                    if (total == 0) {
                        return 0.0;
                    }

                    // Calcul du pourcentage
                    return (double) attended / total * 100.0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors du calcul du taux d'assiduité pour le membre: " + memberId, e);
        }
        return 0.0;
    }



}
