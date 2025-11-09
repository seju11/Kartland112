package com.RaceReserve.Kartland.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF
            .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Enable CORS with config
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                        "/", "/home", "/index",
                        "/favicon.ico", "/static/**", "/css/**", "/js/**", "/images/**"
                ).permitAll()
                .requestMatchers("/api/**").permitAll() // All APIs public for now
                .anyRequest().permitAll() // <-- relaxed for now to avoid login blocking
            )
            .httpBasic(httpBasic -> {}); // Enable Basic Auth if needed

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(Arrays.asList(
            "http://localhost:4200",
            "https://targettallyarena.com",
            "https://www.targettallyarena.com",
            "https://kartlandindia.com",
            "https://www.kartlandindia.com"
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Use NoOp for now (for testing only)
        return org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance();
    }
}
