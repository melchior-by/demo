package pl.ekids.demo.dao;

import pl.ekids.demo.model.Feedback;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PostgresFeedbackDao implements FeedbackDao {
    private final Connection connection;

    public PostgresFeedbackDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Feedback feedback) {
        String sql = "INSERT INTO feedback (name, email, rating, comment, allow_contact, submitted_at) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, feedback.getName());
            stmt.setString(2, feedback.getEmail());
            stmt.setInt(3, feedback.getRating());
            stmt.setString(4, feedback.getComment());
            stmt.setBoolean(5, feedback.isAllowContact());
            stmt.setTimestamp(6, Timestamp.valueOf(feedback.getSubmittedAt()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("❌ [save] failed: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to save feedback", e);
        }
    }

    @Override
    public List<Feedback> findAll() {
        List<Feedback> list = new ArrayList<>();
        String sql = "SELECT * FROM feedback ORDER BY submitted_at DESC";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find all feedback", e);
        }
        return list;
    }

    @Override
    public Optional<Feedback> findById(int id) {
        String sql = "SELECT * FROM feedback WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ [findById] failed: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to find feedback by ID", e);
        }
        return Optional.empty();
    }

    @Override
    public void update(Feedback feedback) {
        String sql = "UPDATE feedback SET name = ?, email = ?, rating = ?, comment = ?, allow_contact = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, feedback.getName());
            stmt.setString(2, feedback.getEmail());
            stmt.setInt(3, feedback.getRating());
            stmt.setString(4, feedback.getComment());
            stmt.setBoolean(5, feedback.isAllowContact());
            stmt.setInt(6, feedback.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("❌ [update] failed: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to update feedback", e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM feedback WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("❌ [delete] failed: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to delete feedback", e);
        }
    }

    private Feedback mapRow(ResultSet rs) throws SQLException {
        return new Feedback(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getInt("rating"),
                rs.getString("comment"),
                rs.getBoolean("allow_contact"),
                rs.getTimestamp("submitted_at").toLocalDateTime()
        );
    }
}
