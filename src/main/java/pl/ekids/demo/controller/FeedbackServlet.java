package pl.ekids.demo.controller;

import pl.ekids.demo.model.Feedback;
import pl.ekids.demo.service.FeedbackService;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/feedback")
public class FeedbackServlet extends HttpServlet {
    private final FeedbackService service = new FeedbackService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Just show the form on GET
        request.getRequestDispatcher("/form.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get form data from request
        String name = request.getParameter("name");
        String ratingStr = request.getParameter("rating");
        String comment = request.getParameter("comment");

        // Basic validation
        if (name == null || name.isBlank() ||
                comment == null || comment.isBlank() ||
                ratingStr == null || !ratingStr.matches("\\d+")) {

            request.setAttribute("error", "All fields are required and rating must be a number.");
            request.getRequestDispatcher("/form.jsp").forward(request, response);
            return;
        }

        int rating = Integer.parseInt(ratingStr);
        if (rating < 1 || rating > 5) {
            request.setAttribute("error", "Rating must be between 1 and 5.");
            request.getRequestDispatcher("/form.jsp").forward(request, response);
            return;
        }

        // Create feedback object
        Feedback feedback = new Feedback(name.trim(), rating, comment.trim());

        // Process through service
        String summary = service.createSummary(feedback);

        // Add result to request + session
        // строка
        request.setAttribute("summary", summary);
        HttpSession session = request.getSession();
        // объект
        session.setAttribute("lastFeedback", feedback);

        request.getRequestDispatcher("/result.jsp").forward(request, response);
    }
}
