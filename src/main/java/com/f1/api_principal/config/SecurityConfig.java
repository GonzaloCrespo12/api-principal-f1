package com.f1.api_principal.config;

import com.f1.api_principal.security.JwtFilter;
import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter; // Inyectamos el filtro

    public SecurityConfig(JwtFilter jwtFilter) { // Constructor
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            //  Apagamos CSRF (Si esto falla, todos los POST dan 403)
            .csrf(csrf -> csrf.disable())
            
            .authorizeHttpRequests(auth -> auth
                //  Permitimos que Spring muestre los errores internos
                .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
                
                //  Permitimos explícitamente el registro
                .requestMatchers(HttpMethod.POST, "/api/usuarios/registro").permitAll()
                // Permitimos explícitamente el login
                .requestMatchers(HttpMethod.POST, "/api/usuarios/login").permitAll()
                // Permitimos que cualquiera vea las escuderías (GET a /api/escuderias/**)
                .requestMatchers(HttpMethod.GET, "/api/escuderias/**").permitAll()
                //  Bloqueamos todo lo demás
                .anyRequest().authenticated()
            )
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}