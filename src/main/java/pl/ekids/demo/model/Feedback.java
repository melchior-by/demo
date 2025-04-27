package pl.ekids.demo.model;

import java.time.LocalDateTime;

public class Feedback {

    private final Integer id;
    private final String name;
    private final String email;
    private final int rating;
    private final String comment;
    private final boolean allowContact;
    private final LocalDateTime submittedAt;

    // Constructor for creating new Feedback (no id, current timestamp)
    public Feedback(String name, String email, int rating, String comment, boolean allowContact) {
        this(null, name, email, rating, comment, allowContact, LocalDateTime.now());
    }

    // Constructor for reading/updating Feedback (id and submittedAt provided)
    public Feedback(Integer id, String name, String email, int rating, String comment, boolean allowContact, LocalDateTime submittedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.rating = rating;
        this.comment = comment;
        this.allowContact = allowContact;
        this.submittedAt = submittedAt;
    }

    // Getters only (immutable)
    public Integer getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public int getRating() { return rating; }
    public String getComment() { return comment; }
    public boolean isAllowContact() { return allowContact; }
    public LocalDateTime getSubmittedAt() { return submittedAt; }
}