package com.aisafe.core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/auth/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/h2-console/**"
                        ).permitAll()

                        .requestMatchers("/routes/**").permitAll()
                        .requestMatchers("/api/airports/**").permitAll()
                        .requestMatchers("/api/aircraft/**").permitAll()
                        .requestMatchers("/api/aircraft-models/**").permitAll()
                        .requestMatchers("/api/maintenance-records/**").permitAll()
                        .requestMatchers("/api/maintenance-templates/**").permitAll()

                        .anyRequest().permitAll()
                );

        return http.build();
    }
}