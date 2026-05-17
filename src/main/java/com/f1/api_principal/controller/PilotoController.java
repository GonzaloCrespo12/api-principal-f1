// Archivo: src/main/java/com/f1/api_principal/controller/PilotoController.java
package com.f1.api_principal.controller;

import com.f1.api_principal.dto.request.PilotoRequestDTO;
import com.f1.api_principal.dto.response.PilotoResponseDTO;
import com.f1.api_principal.service.PilotoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Indica que esta clase maneja rutas de una API REST y devuelve JSON 
@RequestMapping("/api/pilotos") // Ruta base para todos los endpoints de este controlador 
@RequiredArgsConstructor // Lombok inyecta automáticamente el PilotoService por constructor 
public class PilotoController {

    private final PilotoService pilotoService;

    // 1. CREAR UN PILOTO
    @PostMapping // Responde a peticiones HTTP POST 
    public ResponseEntity<PilotoResponseDTO> crear(
            @Valid @RequestBody PilotoRequestDTO dto, // @Valid activa las validaciones del DTO 
            @RequestHeader("X-Escuderia-Id") Long managerEscuderiaId) { // Simulamos la extracción de escudería del JWT 
        
        PilotoResponseDTO nuevoPiloto = pilotoService.crearPiloto(dto, managerEscuderiaId);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPiloto); // Retorna 201 Created [cite: 191, 201]
    }

    // 2. OBTENER TODOS LOS PILOTOS DE MI ESCUDERÍA
    @GetMapping // Responde a peticiones HTTP GET [cite: 73, 74]
    public ResponseEntity<List<PilotoResponseDTO>> listarTodos(
            @RequestHeader("X-Escuderia-Id") Long managerEscuderiaId) {
        
        List<PilotoResponseDTO> pilotos = pilotoService.obtenerPilotosPorEscuderia(managerEscuderiaId);
        return ResponseEntity.ok(pilotos); // Retorna 200 OK con la lista 
    }

    // 3. OBTENER UN PILOTO POR SU ID
    @GetMapping("/{id}") // El ID viene como variable en la URL: /api/pilotos/5 
    public ResponseEntity<PilotoResponseDTO> obtenerPorId(
            @PathVariable Long id, // Captura el valor de {id} de la URL 
            @RequestHeader("X-Escuderia-Id") Long managerEscuderiaId) {
        
        PilotoResponseDTO piloto = pilotoService.obtenerPilotoPorId(id, managerEscuderiaId);
        return ResponseEntity.ok(piloto); // Retorna 200 OK 
    }

    // 4. ACTUALIZAR UN PILOTO
    @PutMapping("/{id}") // Responde a peticiones HTTP PUT 
    public ResponseEntity<PilotoResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody PilotoRequestDTO dto,
            @RequestHeader("X-Escuderia-Id") Long managerEscuderiaId) {
        
        PilotoResponseDTO actualizado = pilotoService.actualizarPiloto(id, dto, managerEscuderiaId);
        return ResponseEntity.ok(actualizado); // Retorna 200 OK 
    }

    // 5. ELIMINAR UN PILOTO (Borrado lógico o físico automático en el Service)
    @DeleteMapping("/{id}") // Responde a peticiones HTTP DELETE 
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id,
            @RequestHeader("X-Escuderia-Id") Long managerEscuderiaId) {
        
        pilotoService.eliminarPiloto(id, managerEscuderiaId);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content si todo sale bien 
    }
}