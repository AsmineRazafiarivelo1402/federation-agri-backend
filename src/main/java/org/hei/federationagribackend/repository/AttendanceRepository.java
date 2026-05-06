package org.hei.federationagribackend.repository;

import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

}
