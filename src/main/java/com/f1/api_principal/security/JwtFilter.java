// Archivo: src/main/java/com/f1/api_principal/security/JwtFilter.java
package com.f1.api_principal.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
            throws ServletException, IOException {
        
        // 1. Buscamos el token en la cabecera "Authorization"
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        // 2. Verificamos que venga con el formato correcto (Bearer eyJ...)
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7); // Cortamos la palabra "Bearer "
            try {
                username = jwtUtil.extraerUsername(token);
            } catch (Exception e) {
                // Si el token es inválido, lo ignoramos y dejamos que Spring Security bloquee (403)
            }
        }

        // 3. Si encontramos un usuario y no está autenticado aún en este ciclo
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (jwtUtil.validarToken(token)) {
                // Creamos el pase oficial de Spring Security y lo guardamos en el Contexto
                UsernamePasswordAuthenticationToken authToken = 
                        new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // 4. Dejamos que la petición siga su curso (hacia el Controller)
        filterChain.doFilter(request, response);
    }
}