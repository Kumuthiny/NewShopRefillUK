package com.example.shop.config;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthFilter extends OncePerRequestFilter {

    private static final String USERNAME = "username";
    private static final String PASSWORD = "Password@246";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        String path = request.getRequestURI();

        // always allow login page and static assets
        if (path.equals("/login") || path.startsWith("/css") || path.startsWith("/js")
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
    }
}
