// Archivo: src/main/java/com/f1/api_principal/controller/EscuderiaController.java
package com.f1.api_principal.controller;

import com.f1.api_principal.dto.response.EscuderiaResponseDTO;
import com.f1.api_principal.service.EscuderiaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/escuderias")
public class EscuderiaController {

    private final EscuderiaService escuderiaService;

    public EscuderiaController(EscuderiaService escuderiaService) {
        this.escuderiaService = escuderiaService;
    }

    @GetMapping
    public ResponseEntity<List<EscuderiaResponseDTO>> obtenerTodas() {
        return ResponseEntity.ok(escuderiaService.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EscuderiaResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(escuderiaService.obtenerPorId(id));
    }
}