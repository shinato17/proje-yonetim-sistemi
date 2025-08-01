package com.proje.pys.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proje.pys.entity.Kullanici;
import com.proje.pys.service.KullaniciServisi;

@RestController
@RequestMapping("/api/debug")
public class DebugController {

    private static final Logger logger = LoggerFactory.getLogger(DebugController.class);

    @Autowired
    private KullaniciServisi kullaniciServisi;

    @GetMapping("/auth-test")
    public ResponseEntity<Map<String, Object>> authTest(
            @AuthenticationPrincipal User user,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Authentication bilgilerini kontrol et
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            
            logger.info("=== AUTH DEBUG ===");
            logger.info("Authorization Header: {}", authHeader);
            logger.info("Authentication: {}", auth != null ? auth.toString() : "NULL");
            logger.info("User: {}", user != null ? user.getUsername() : "NULL");
            logger.info("Principal: {}", auth != null ? auth.getPrincipal() : "NULL");
            logger.info("Authorities: {}", auth != null ? auth.getAuthorities() : "NULL");
            logger.info("Is Authenticated: {}", auth != null ? auth.isAuthenticated() : "NULL");
            
            response.put("authHeader", authHeader);
            response.put("hasAuthentication", auth != null);
            response.put("hasUser", user != null);
            response.put("isAuthenticated", auth != null ? auth.isAuthenticated() : false);
            
            if (auth != null) {
                response.put("principal", auth.getPrincipal().toString());
                response.put("authorities", auth.getAuthorities().toString());
            }
            
            if (user != null) {
                response.put("username", user.getUsername());
                response.put("authorities", user.getAuthorities().toString());
                
                // Kullanıcıyı veritabanından bul
                Kullanici kullanici = kullaniciServisi.findByEposta(user.getUsername());
                if (kullanici != null) {
                    response.put("dbUser", Map.of(
                        "id", kullanici.getId(),
                        "isim", kullanici.getIsim(),
                        "eposta", kullanici.getEposta()
                    ));
                } else {
                    response.put("dbUser", "NOT_FOUND");
                }
            }
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Auth test sırasında hata: ", e);
            response.put("error", e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
}