package com.example.client.security;

import com.example.common.security.SecurityUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class SidValidationFilter extends OncePerRequestFilter {
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                  HttpServletResponse response, 
                                  FilterChain filterChain) throws ServletException, IOException {
        String sid = SecurityUtils.extractSid(request.getHeader(SecurityUtils.getSidHeaderName()));
        
        if (!SecurityUtils.isValidSid(sid)) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Invalid or missing session ID");
            return;
        }
        
        filterChain.doFilter(request, response);
    }
} 