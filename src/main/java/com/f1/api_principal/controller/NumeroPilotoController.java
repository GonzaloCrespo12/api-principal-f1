// Archivo: src/main/java/com/f1/api_principal/controller/NumeroPilotoController.java
package com.f1.api_principal.controller;

import com.f1.api_principal.dto.response.NumeroPilotoResponseDTO;
import com.f1.api_principal.service.NumeroPilotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/numeros")
@RequiredArgsConstructor
public class NumeroPilotoController {

    private final NumeroPilotoService numeroPilotoService;

    // GET /api/numeros
    @GetMapping
    public ResponseEntity<List<NumeroPilotoResponseDTO>> listarTodos() {
        List<NumeroPilotoResponseDTO> lista = numeroPilotoService.listarTodos();
        return ResponseEntity.ok(lista);
    }

    // GET /api/numeros/disponibles
    @GetMapping("/disponibles")
    public ResponseEntity<List<NumeroPilotoResponseDTO>> listarDisponibles() {
        List<NumeroPilotoResponseDTO> lista = numeroPilotoService.listarDisponibles();
        return ResponseEntity.ok(lista);
    }
}