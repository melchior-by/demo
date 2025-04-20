package pl.ekids.demo.dao;

import pl.ekids.demo.model.Feedback;
import java.util.List;
import java.util.Optional;

public interface FeedbackDao {
    void save(Feedback feedback);
    List<Feedback> findAll();
    Optional<Feedback> findById(int id);
    void update(Feedback feedback);
    void delete(int id);
}

