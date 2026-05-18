package com.f1.api_principal.controller;

import com.f1.api_principal.dto.response.ResultadoResponseDTO;
import com.f1.api_principal.service.ResultadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/resultados")
@RequiredArgsConstructor
public class ResultadoController {

    private final ResultadoService resultadoService;

    @GetMapping // GET /api/resultados
    public ResponseEntity<List<ResultadoResponseDTO>> listarHistorial(
            @RequestHeader("X-Escuderia-Id") Long managerEscuderiaId) {
        
        List<ResultadoResponseDTO> historial = resultadoService.obtenerHistorialPorEscuderia(managerEscuderiaId);
        return ResponseEntity.ok(historial); // Devuelve el historial en formato JSON con un 200 OK
    }
}