package com.proje.pys.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;


import com.proje.pys.service.KullaniciDetayServisi;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtTokenUtil jwtTokenUtil;
    private final KullaniciDetayServisi kullaniciDetayServisi;

    public JwtAuthFilter(JwtTokenUtil jwtTokenUtil, KullaniciDetayServisi kullaniciDetayServisi) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.kullaniciDetayServisi = kullaniciDetayServisi;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);
        final String eposta = jwtTokenUtil.extractUsername(jwt);

        if (eposta != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.kullaniciDetayServisi.loadUserByUsername(eposta);
            if (jwtTokenUtil.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
