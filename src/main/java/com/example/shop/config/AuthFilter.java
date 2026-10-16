package com.example.shop.config;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(AuthFilter.class);
    private static final String USERNAME = "username";
    private static final String PASSWORD = "Password@246";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        long startTime = System.currentTimeMillis();
        String path = request.getRequestURI();

        // always allow login page, logout, and static assets
        if (path.equals("/login") || path.startsWith("/logout") || path.startsWith("/css") || path.startsWith("/js")
                || path.startsWith("/api") || path.equals("/error") || path.startsWith("/error")) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = request.getSession(false);
        boolean loggedIn = session != null && Boolean.TRUE.equals(session.getAttribute("loggedIn"));

        if (!loggedIn) {
            response.sendRedirect("/login");
            return;
        }

        chain.doFilter(request, response);
        long duration = System.currentTimeMillis() - startTime;
        if (duration > 100) {
            log.warn("Slow request: {} took {}ms", path, duration);
        }
    }
}
