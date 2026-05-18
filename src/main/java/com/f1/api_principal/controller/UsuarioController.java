// Archivo: src/main/java/com/f1/api_principal/controller/UsuarioController.java
package com.f1.api_principal.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.f1.api_principal.dto.UsuarioLoginDTO;
import com.f1.api_principal.dto.request.UsuarioCreateDTO;
import com.f1.api_principal.dto.response.UsuarioResponseDTO;
import com.f1.api_principal.service.UsuarioService;

import jakarta.validation.Valid;

@RestController // Indica que esta clase maneja peticiones web y devuelve JSON 
@RequestMapping("/api/usuarios") // Define la ruta base para este controlador 
public class UsuarioController {

    private final UsuarioService usuarioService;

    // Inyectamos el servicio por constructor 
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Endpoint POST para registrar un nuevo usuario 
    @PostMapping("/registro")
    public ResponseEntity<UsuarioResponseDTO> registrarUsuario(@Valid @RequestBody UsuarioCreateDTO dto) {
        
        // Llamamos a la lógica de negocio que creaste antes
        UsuarioResponseDTO usuarioCreado = usuarioService.registrarUsuario(dto);
        
        // Devolvemos el usuario creado con un código 201 (Created) 
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCreado);
    }

    // Pega este método debajo de tu método de registrarUsuario
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody UsuarioLoginDTO dto) {
        // Llamamos al servicio para que valide y genere el token
        String token = usuarioService.login(dto);
        
        // Devolvemos el token dentro de un JSON estructurado de forma prolija: {"token": "ey..."}
        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }
}
