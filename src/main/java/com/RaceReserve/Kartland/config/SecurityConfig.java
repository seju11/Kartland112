// package com.RaceReserve.Kartland.config;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;

// import org.springframework.web.cors.CorsConfiguration;
// import org.springframework.web.cors.CorsConfigurationSource;
// import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

// import java.util.Arrays;

// @Configuration
// public class SecurityConfig {

//     @Bean
//     public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//         http
//             .csrf().disable() // Disable CSRF for simplicity
//             .cors() // Enable CORS
//             .and()
//             .authorizeHttpRequests(auth -> auth
//                 .requestMatchers(
//                         "/", "/home", "/index", 
//                         "/favicon.ico", "/static/**", "/css/**", "/js/**", "/images/**"
//                 ).permitAll() // Public paths
//                 .requestMatchers("/api/**").permitAll() // Public API
//                 .anyRequest().authenticated() // All other endpoints require auth
//             )
//             .httpBasic(); // Basic auth for protected endpoints

//         return http.build();
//     }

//     @Bean
//     public CorsConfigurationSource corsConfigurationSource() {
//         CorsConfiguration configuration = new CorsConfiguration();
//         configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200",)); // Angular origin
//         configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//         configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With"));
//         configuration.setAllowCredentials(true); // Important: allow cookies/session to be sent

//         UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//         source.registerCorsConfiguration("/**", configuration);

//         return source;
//     }

//     @Bean
//     public PasswordEncoder passwordEncoder() {
//             return org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance();
//     }
// }



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
            .csrf().disable() // Disable CSRF
            .cors() // Enable CORS
            .and()
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                        "/", "/home", "/index",
                        "/favicon.ico", "/static/**", "/css/**", "/js/**", "/images/**"
                ).permitAll() // Public paths
                .requestMatchers("/api/**").permitAll() // Public API endpoints
                .anyRequest().authenticated()
            )
            .httpBasic(); // Basic auth for protected endpoints

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // Allow both local Angular dev and deployed frontend
        configuration.setAllowedOrigins(Arrays.asList(
            "http://localhost:4200",
            "https://targettallyarena.com"
        ));
        
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With"));
        configuration.setAllowCredentials(true); // allow cookies/session

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Use NoOp for testing; change to BCrypt in production
        return org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance();
    }
}
