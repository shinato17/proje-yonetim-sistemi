package com.proje.pys.controller;

import com.proje.pys.config.JwtTokenUtil;
import com.proje.pys.model.AuthRequest;
import com.proje.pys.model.AuthResponse;
import com.proje.pys.service.KullaniciDetayServisi;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final KullaniciDetayServisi kullaniciDetayServisi;
    private final JwtTokenUtil jwtTokenUtil;

    public AuthController(AuthenticationManager authenticationManager,
                          KullaniciDetayServisi kullaniciDetayServisi,
                          JwtTokenUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.kullaniciDetayServisi = kullaniciDetayServisi;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.eposta(),
                        request.sifre()
                )
        );

        UserDetails userDetails = kullaniciDetayServisi.loadUserByUsername(request.eposta());
        String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthResponse(token));
    }
}
