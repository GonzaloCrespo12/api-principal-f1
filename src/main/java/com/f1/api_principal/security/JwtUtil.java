// Archivo: src/main/java/com/f1/api_principal/security/JwtUtil.java
package com.f1.api_principal.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component // Le indica a Spring que administre esta clase para poder usarla en otros lados
public class JwtUtil {

    // Generamos una clave secreta criptográfica segura para firmar los tokens
    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    
    // Tiempo de validez del token: 1 día (en milisegundos)
    private final long tiempoExpiracion = 86400000; 

    // Método para fabricar el token JWT
    public String generarToken(String username) {
        return Jwts.builder()
                .setSubject(username) // Guardamos quién es el usuario
                .setIssuedAt(new Date()) // Fecha de creación
                .setExpiration(new Date(System.currentTimeMillis() + tiempoExpiracion)) // Fecha de vencimiento
                .signWith(secretKey) // Firmamos el token con nuestra clave secreta
                .compact(); // Lo armamos en un solo String listo para enviar
    }

    // Extrae el nombre de usuario que guardamos dentro del token
    public String extraerUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Verifica que el token esté firmado por nosotros y no haya expirado
    public boolean validarToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false; // Si está alterado o vencido, devuelve falso
        }
    }
}