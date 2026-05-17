// Archivo: src/main/java/com/f1/api_principal/controller/MotoristaController.java
package com.f1.api_principal.controller;

import com.f1.api_principal.dto.response.MotoristaResponseDTO;
import com.f1.api_principal.service.MotoristaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/motoristas")
@RequiredArgsConstructor
public class MotoristaController {

    private final MotoristaService motoristaService;

    @GetMapping
    public ResponseEntity<List<MotoristaResponseDTO>> listarMotoristas() {
        List<MotoristaResponseDTO> lista = motoristaService.listarTodos();
        return ResponseEntity.ok(lista); // Retorna 200 OK con el listado de motoristas
    }
}