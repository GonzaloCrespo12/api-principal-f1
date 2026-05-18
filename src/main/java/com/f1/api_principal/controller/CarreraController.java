package com.f1.api_principal.controller;

import com.f1.api_principal.dto.response.CarreraResponseDTO;
import com.f1.api_principal.service.CarreraService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController // Habilita la clase para responder JSON en la web
@RequestMapping("/api/carreras") 
@RequiredArgsConstructor
public class CarreraController {

    private final CarreraService carreraService;

    @GetMapping
    public ResponseEntity<List<CarreraResponseDTO>> obtenerCalendario(
            // El parámetro esSprint es opcional, si no se proporciona, se mostrarán todas las carreras
            @RequestParam(required = false) Boolean esSprint) {
        
        List<CarreraResponseDTO> calendario = carreraService.obtenerCalendario(esSprint);
        return ResponseEntity.ok(calendario);
    }
}