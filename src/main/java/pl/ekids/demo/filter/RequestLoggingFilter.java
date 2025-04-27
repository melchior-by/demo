package pl.ekids.demo.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

@WebFilter("/*")
public class RequestLoggingFilter implements Filter {
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        System.out.println("➡️ Incoming request: " + req.getRequestURI() + (req.getQueryString() != null ? "?" + req.getQueryString() : ""));
        chain.doFilter(request, response);
    }
}
