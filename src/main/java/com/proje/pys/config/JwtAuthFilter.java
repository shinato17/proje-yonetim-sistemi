package com.proje.pys.config;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.proje.pys.service.KullaniciDetayServisi;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);
    
    private final JwtTokenUtil jwtTokenUtil;
    private final KullaniciDetayServisi kullaniciDetayServisi;

    public JwtAuthFilter(JwtTokenUtil jwtTokenUtil, KullaniciDetayServisi kullaniciDetayServisi) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.kullaniciDetayServisi = kullaniciDetayServisi;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String token;
        final String username;

        logger.debug("=== JWT FILTER ===");
        logger.debug("Request URI: {}", request.getRequestURI());
        logger.debug("Auth Header: {}", authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            logger.debug("No Authorization header or doesn't start with Bearer");
            filterChain.doFilter(request, response);
            return;
        }

        try {
            token = authHeader.substring(7);
            logger.debug("Token extracted: {}", token.substring(0, Math.min(20, token.length())) + "...");
            
            username = jwtTokenUtil.extractUsername(token);
            logger.debug("Username extracted: {}", username);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                logger.debug("Loading user details for: {}", username);
                UserDetails userDetails = this.kullaniciDetayServisi.loadUserByUsername(username);
                logger.debug("User details loaded: {}", userDetails != null ? userDetails.getUsername() : "NULL");

                if (jwtTokenUtil.validateToken(token, userDetails)) {
                    logger.debug("Token is valid, setting authentication");
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    logger.debug("Authentication set successfully");
                } else {
                    logger.warn("Token validation failed");
                }
            } else {
                logger.debug("Username is null or authentication already exists");
            }
        } catch (Exception e) {
            logger.error("JWT processing error: ", e);
        }

        filterChain.doFilter(request, response);
    }
}