package pl.ekids.demo.controller;

import pl.ekids.demo.dao.DaoFactory;
import pl.ekids.demo.dao.FeedbackDao;
import pl.ekids.demo.model.Feedback;
import pl.ekids.demo.controller.Action;
import pl.ekids.demo.service.FeedbackService;
import pl.ekids.demo.service.ValidationException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet("/feedback")
public class FeedbackServlet extends HttpServlet {

    private FeedbackService service;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MMMM dd, yyyy HH:mm");

    @Override
    public void init() throws ServletException {
        FeedbackDao dao = DaoFactory.getDao();
        service = new FeedbackService(dao);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Action action = Action.from(request.getParameter("action"));

        if (action == null) {
            response.sendRedirect("form.jsp");
            return;
        }

        try {
            switch (action) {
                case LIST -> listFeedback(request, response);
                case VIEW -> viewFeedback(request, response);
                case EDIT -> editFeedback(request, response);
                case DELETE -> deleteFeedback(request, response);
                default -> response.sendRedirect("form.jsp");
            }
        } catch (ValidationException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/feedback-error.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Unexpected error: " + e.getMessage());
            request.getRequestDispatcher("/feedback-error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String ratingStr = request.getParameter("rating");
        String comment = request.getParameter("comment");
        boolean allowContact = request.getParameter("allowContact") != null;

        try {
            if (idStr == null || idStr.isBlank()) {
                createFeedback(request, response, name, email, ratingStr, comment, allowContact);
            } else {
                updateFeedback(request, response, idStr, name, email, ratingStr, comment, allowContact);
            }
        } catch (ValidationException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/form.jsp").forward(request, response);
        }
    }

    private void listFeedback(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Feedback> feedbackList = service.findAllFeedbacks();
        request.setAttribute("feedbackList", feedbackList);
        request.getRequestDispatcher("/feedback-list.jsp").forward(request, response);
    }

    private void viewFeedback(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Feedback feedback = service.findFeedbackById(id).orElseThrow(() -> new ValidationException("Feedback not found: ID = " + id));
        request.setAttribute("feedback", feedback);
        request.getRequestDispatcher("/feedback-detail.jsp").forward(request, response);
    }

    private void editFeedback(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Feedback feedback = service.findFeedbackById(id).orElseThrow(() -> new ValidationException("Feedback not found: ID = " + id));
        request.setAttribute("feedback", feedback);
        request.getRequestDispatcher("/feedback-edit.jsp").forward(request, response);
    }

    private void deleteFeedback(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        service.deleteFeedback(id);
        response.sendRedirect("feedback?action=list");
    }

    private void createFeedback(HttpServletRequest request, HttpServletResponse response, String name, String email, String ratingStr, String comment, boolean allowContact) throws ServletException, IOException {
        Feedback feedback = service.createValidatedFeedback(name, email, ratingStr, comment, allowContact);
        service.saveFeedback(feedback);

        String formattedDate = feedback.getSubmittedAt().format(DATE_FORMATTER);
        request.setAttribute("formattedDate", formattedDate);
        request.setAttribute("feedback", feedback);

        HttpSession session = request.getSession();
        session.setAttribute("lastFeedback", feedback);

        request.getRequestDispatcher("/result.jsp").forward(request, response);
    }

    private void updateFeedback(HttpServletRequest request, HttpServletResponse response, String idStr, String name, String email, String ratingStr, String comment, boolean allowContact) throws IOException {
        int id = Integer.parseInt(idStr);

        Feedback oldFeedback = service.findFeedbackById(id)
                .orElseThrow(() -> new ValidationException("Feedback not found for update: ID = " + id));

        Feedback feedback = new Feedback(
                id,
                name.trim(),
                email.trim(),
                Integer.parseInt(ratingStr),
                comment.trim(),
                allowContact,
                oldFeedback.getSubmittedAt()
        );

        service.updateFeedback(feedback);

        response.sendRedirect("feedback?action=view&id=" + id);
    }
}