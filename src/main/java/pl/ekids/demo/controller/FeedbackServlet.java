package pl.ekids.demo.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import pl.ekids.demo.dao.DaoFactory;
import pl.ekids.demo.dao.FeedbackDao;
import pl.ekids.demo.model.Feedback;
import pl.ekids.demo.service.FeedbackService;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet("/feedback")
public class FeedbackServlet extends HttpServlet {
    private FeedbackService service;
    private FeedbackDao dao;

    @Override
    public void init() throws ServletException {
        dao = DaoFactory.getDao();
        service = new FeedbackService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //                          "view"
        Action action = Action.from(request.getParameter("action"));

        if (action == null) {
            response.sendRedirect("form.jsp");
            return;
        }

        try {
            switch (action) {
                case LIST -> {
                    List<Feedback> feedbackList = dao.findAll();
                    request.setAttribute("feedbackList", feedbackList);
                    request.getRequestDispatcher("/feedback-list.jsp").forward(request, response);
                }
                case VIEW -> {
                    int id = Integer.parseInt(request.getParameter("id"));
                    Feedback feedback = dao.findById(id).orElseThrow();
                    request.setAttribute("feedback", feedback);
                    request.getRequestDispatcher("/feedback-detail.jsp").forward(request, response);
                }
                case EDIT -> {
                    int id = Integer.parseInt(request.getParameter("id"));
                    Feedback feedback = dao.findById(id).orElseThrow();
                    request.setAttribute("feedback", feedback);
                    request.getRequestDispatcher("/feedback-edit.jsp").forward(request, response);
                }
                case DELETE -> {
                    int id = Integer.parseInt(request.getParameter("id"));
                    dao.delete(id);
                    response.sendRedirect("feedback?action=list");
                }
                default -> response.sendRedirect("form.jsp");
            }
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Ошибка: " + e.getMessage());
            request.getRequestDispatcher("/feedback-error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String ratingStr = request.getParameter("rating");
        String comment = request.getParameter("comment");
        boolean allowContact = request.getParameter("allowContact") != null;

        if (name == null || name.isBlank() || email == null || email.isBlank()
                || ratingStr == null || !ratingStr.matches("\\d+") || comment == null || comment.isBlank()) {
            request.setAttribute("error", "All fields are required and rating must be numeric.");
            request.getRequestDispatcher("/form.jsp").forward(request, response);
            return;
        }

        int rating = Integer.parseInt(ratingStr);
        if (rating < 1 || rating > 5) {
            request.setAttribute("error", "Rating must be between 1 and 5.");
            request.getRequestDispatcher("/form.jsp").forward(request, response);
            return;
        }

        // Create Feedback object
        Feedback feedback = new Feedback(name.trim(), email.trim(), rating, comment.trim(), allowContact);
        dao.save(feedback);

        // Format LocalDateTime for JSP display
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy HH:mm");
        String formattedDate = feedback.getSubmittedAt().format(formatter);
        request.setAttribute("formattedDate", formattedDate);

        // Process message
        String summary = service.createSummary(feedback);
        request.setAttribute("summary", summary);

        HttpSession session = request.getSession();
        session.setAttribute("lastFeedback", feedback);

        request.getRequestDispatcher("/result.jsp").forward(request, response);
    }
}
