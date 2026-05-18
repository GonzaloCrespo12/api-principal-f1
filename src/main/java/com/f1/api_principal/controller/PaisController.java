package com.f1.api_principal.controller;

import com.f1.api_principal.dto.response.PaisResponseDTO;
import com.f1.api_principal.service.PaisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/paises")
@RequiredArgsConstructor
public class PaisController {

    private final PaisService paisService;

    @GetMapping
    public ResponseEntity<List<PaisResponseDTO>> listarPaises() {
        List<PaisResponseDTO> lista = paisService.listarTodos();
        return ResponseEntity.ok(lista); // Retorna 200 OK con el array de países
    }
}