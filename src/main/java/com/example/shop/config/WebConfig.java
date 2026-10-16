package com.example.shop.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
                // Ensure session is created if it doesn't exist for authenticated requests
                String path = request.getRequestURI();
                if (!path.equals("/login") && !path.startsWith("/logout") && 
                    !path.startsWith("/css") && !path.startsWith("/js") && 
                    !path.startsWith("/api") && !path.equals("/error")) {
                    request.getSession(true);
                }
                return true;
            }
        });
    }
}
