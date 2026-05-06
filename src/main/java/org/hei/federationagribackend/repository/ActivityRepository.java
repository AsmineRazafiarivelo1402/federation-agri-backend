package org.hei.federationagribackend.repository;

import org.hei.federationagribackend.entity.Activity;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class ActivityRepository {
    private final Connection connection;

    public ActivityRepository(Connection connection) {
        this.connection = connection;
    }

    public void save(Activity activity) {
        String sql = "INSERT INTO activity (id, collectivity_id, label, activity_type, executive_date, week_ordinal, day_of_week) " +
                "VALUES (?, ?, ?, ?::activity_type_enum, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, activity.getId());
            statement.setString(2, activity.getCollectivityId());
            statement.setString(3, activity.getLabel());
            statement.setString(4, activity.getActivityType());

            statement.setObject(5, activity.getExecutiveDate());
            statement.setObject(6, activity.getWeekOrdinal());
            statement.setString(7, activity.getDayOfWeek());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'insertion de l'activité", e);
        }
    }

    public void saveOccupation(String activityId, String occupation) {
        String sql = "INSERT INTO activity_occupation_concerned (activity_id, occupation) VALUES (?, ?::member_occupation)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, activityId);
            statement.setString(2, occupation);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'insertion de l'occupation", e);
        }
    }
}
