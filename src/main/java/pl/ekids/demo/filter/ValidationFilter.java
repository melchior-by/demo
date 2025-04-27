package pl.ekids.demo.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

@WebFilter("/feedback")
public class ValidationFilter implements Filter {
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        if ("POST".equalsIgnoreCase(req.getMethod())) {
            String name = req.getParameter("name");
            if (name == null || name.trim().isEmpty()) {
                request.setAttribute("errorMessage", "Name cannot be empty (blocked by Filter)");
                request.getRequestDispatcher("/feedback-error.jsp").forward(request, response);
                return;
            }
        }
        chain.doFilter(request, response);
    }
}
