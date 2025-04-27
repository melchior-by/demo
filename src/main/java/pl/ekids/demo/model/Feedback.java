package pl.ekids.demo.model;

import java.time.LocalDateTime;

// A simple POJO to store feedback data
public class Feedback {
    private int id;
    private String name;
    private String email;
    private int rating;
    private String comment;
    private boolean allowContact;
    private LocalDateTime submittedAt;

    // ✅ Constructor for new feedback — no ID, timestamp is now()
    public Feedback(String name, String email, int rating, String comment, boolean allowContact) {
        this(0, name, email, rating, comment, allowContact, LocalDateTime.now());
    }

    // ✅ Main constructor with ID and timestamp
    public Feedback(int id, String name, String email, int rating, String comment, boolean allowContact, LocalDateTime submittedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.rating = rating;
        this.comment = comment;
        this.allowContact = allowContact;
        this.submittedAt = submittedAt;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public int getRating() { return rating; }
    public String getComment() { return comment; }
    public boolean isAllowContact() { return allowContact; }
    public LocalDateTime getSubmittedAt() { return submittedAt; }

    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", allowContact=" + allowContact +
                ", submittedAt=" + submittedAt +
                '}';
    }
}
