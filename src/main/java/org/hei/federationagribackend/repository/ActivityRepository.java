package org.hei.federationagribackend.repository;

import org.hei.federationagribackend.entity.Activity;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ActivityRepository {
    private final Connection connection;

    public ActivityRepository(Connection connection) {
        this.connection = connection;
    }

    public List<String> findOccupationsByActivityId(String activityId) {
        List<String> occupations = new ArrayList<>();
        String sql = "SELECT occupation FROM activity_occupation_concerned WHERE activity_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, activityId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    occupations.add(resultSet.getString("occupation"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return occupations;
    }

    public List<Activity> findAllByCollectivityId(String collectivityId) {
        List<Activity> activities = new ArrayList<>();
        String sql = "SELECT * FROM activity WHERE collectivity_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, collectivityId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Activity activity = new Activity();
                    activity.setId(resultSet.getString("id"));
                    activity.setLabel(resultSet.getString("label"));
                    activity.setActivityType(resultSet.getString("activity_type"));

                    Date sqlDate = resultSet.getDate("executive_date");
                    if (sqlDate != null) {
                        activity.setExecutiveDate(sqlDate.toLocalDate());
                    }

                    activity.setWeekOrdinal(resultSet.getObject("week_ordinal", Integer.class));
                    activity.setDayOfWeek(resultSet.getString("day_of_week"));
                    // Juste avant d'ajouter l'activité à la liste
                    activity.setMemberOccupation(findOccupationsByActivityId(activity.getId()));

                    activities.add(activity);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activities;
    }

    public void save(Activity activity, List<String> memberOccupationConcerned) {
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
