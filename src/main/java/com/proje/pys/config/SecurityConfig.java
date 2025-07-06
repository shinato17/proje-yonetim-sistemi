package com.proje.pys.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()

                        // Proje işlemleri - Yönetici yetkisi gerektirenler
                        .requestMatchers(HttpMethod.POST, "/api/projeler").hasAuthority("Yönetici")
                        .requestMatchers(HttpMethod.DELETE, "/api/projeler/**").hasAuthority("Yönetici")
                        .requestMatchers(HttpMethod.PUT, "/api/projeler/**").hasAuthority("Yönetici")

                        // Kullanıcı işlemleri - Yönetici yetkisi gerektirenler
                        .requestMatchers(HttpMethod.POST, "/api/kullanicilar").hasAuthority("Yönetici")
                        .requestMatchers(HttpMethod.DELETE, "/api/kullanicilar/**").hasAuthority("Yönetici")
                        .requestMatchers(HttpMethod.PUT, "/api/kullanicilar/**").hasAuthority("Yönetici")

                        // Proje kullanıcı atama işlemleri - Yönetici yetkisi gerektirenler
                        .requestMatchers(HttpMethod.POST, "/api/proje-kullanicilari/**").hasAuthority("Yönetici")
                        .requestMatchers(HttpMethod.DELETE, "/api/proje-kullanicilari/**").hasAuthority("Yönetici")

                        // Rol işlemleri - Yönetici yetkisi gerektirenler
                        .requestMatchers(HttpMethod.POST, "/api/roller").hasAuthority("Yönetici")
                        .requestMatchers(HttpMethod.PUT, "/api/roller/**").hasAuthority("Yönetici")
                        .requestMatchers(HttpMethod.DELETE, "/api/roller/**").hasAuthority("Yönetici")

                        // Proje durum işlemleri - Yönetici yetkisi gerektirenler
                        .requestMatchers(HttpMethod.POST, "/api/proje-durumlari").hasAuthority("Yönetici")
                        .requestMatchers(HttpMethod.PUT, "/api/proje-durumlari/**").hasAuthority("Yönetici")
                        .requestMatchers(HttpMethod.DELETE, "/api/proje-durumlari/**").hasAuthority("Yönetici")

                        // Diğer endpointler
                        .requestMatchers("/api/projeler/**").hasAnyAuthority("Yönetici", "Çalışan")
                        .requestMatchers("/api/kullanicilar/**").authenticated()
                        .requestMatchers("/api/roller/**").authenticated()
                        .requestMatchers("/api/proje-durumlari/**").authenticated()
                        .requestMatchers("/api/proje-kullanicilari/**").authenticated()

                        .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}