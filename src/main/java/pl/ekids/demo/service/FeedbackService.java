package pl.ekids.demo.service;

import pl.ekids.demo.dao.FeedbackDao;
import pl.ekids.demo.model.Feedback;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class FeedbackService {

    private static final Logger logger = Logger.getLogger(FeedbackService.class.getName());

    private final FeedbackDao feedbackDao;

    public FeedbackService(FeedbackDao feedbackDao) {
        if (feedbackDao == null) {
            throw new IllegalArgumentException("FeedbackDao must not be null");
        }
        this.feedbackDao = feedbackDao;
    }

    public Feedback createValidatedFeedback(String name, String email, String ratingStr, String comment, boolean allowContact) {
        if (isBlank(name) || isBlank(email) || isBlank(ratingStr) || isBlank(comment)) {
            throw new ValidationException("All fields are required.");
        }
        if (!ratingStr.matches("\\d+")) {
            throw new ValidationException("Rating must be a number.");
        }
        int rating = Integer.parseInt(ratingStr);
        if (rating < 1 || rating > 5) {
            throw new ValidationException("Rating must be between 1 and 5.");
        }

        Feedback feedback = new Feedback(name.trim(), email.trim(), rating, comment.trim(), allowContact);
        logger.info("Created feedback: " + feedback.getName() + ", rating: " + feedback.getRating());
        return feedback;
    }

    public void saveFeedback(Feedback feedback) {
        feedbackDao.save(feedback);
        logger.info("Saved feedback with ID: " + feedback.getId());
    }

    public List<Feedback> findAllFeedbacks() {
        return feedbackDao.findAll();
    }

    public Optional<Feedback> findFeedbackById(int id) {
        return feedbackDao.findById(id);
    }

    public void updateFeedback(Feedback feedback) {
        if (feedback == null || feedback.getId() == null) {
            throw new ValidationException("Feedback and ID must not be null for update.");
        }
        feedbackDao.update(feedback);
        logger.info("Updated feedback with ID: " + feedback.getId());
    }

    public void deleteFeedback(int id) {
        feedbackDao.delete(id);
        logger.info("Deleted feedback with ID: " + id);
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
