package com.f1.api_principal.controller;

import com.f1.api_principal.dto.request.PilotoRequestDTO;
import com.f1.api_principal.dto.response.PilotoResponseDTO;
import com.f1.api_principal.service.PilotoService;
import com.f1.api_principal.service.UsuarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController 
@RequestMapping("/api/pilotos") 
@RequiredArgsConstructor 
public class PilotoController {

    private final PilotoService pilotoService;
    private final UsuarioService usuarioService;

    // CREAR UN PILOTO
    @PostMapping 
    public ResponseEntity<PilotoResponseDTO> crear(@Valid @RequestBody PilotoRequestDTO dto) { 
        // Obtenemos la escudería directamente del Token JWT
        Long managerEscuderiaId = usuarioService.obtenerEscuderiaIdDelUsuarioAutenticado();
        
        PilotoResponseDTO nuevoPiloto = pilotoService.crearPiloto(dto, managerEscuderiaId);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPiloto); 
    }

    //  OBTENER TODOS LOS PILOTOS DE MI ESCUDERÍA
    @GetMapping 
    public ResponseEntity<List<PilotoResponseDTO>> listarTodos() {
        // Obtenemos la escudería directamente del Token JWT
        Long managerEscuderiaId = usuarioService.obtenerEscuderiaIdDelUsuarioAutenticado();
        
        List<PilotoResponseDTO> pilotos = pilotoService.obtenerPilotosPorEscuderia(managerEscuderiaId);
        return ResponseEntity.ok(pilotos); 
    }

    // OBTENER UN PILOTO POR SU ID
    @GetMapping("/{id}") 
    public ResponseEntity<PilotoResponseDTO> obtenerPorId(@PathVariable Long id) {
        //  Obtenemos la escudería directamente del Token JWT
        Long managerEscuderiaId = usuarioService.obtenerEscuderiaIdDelUsuarioAutenticado();
        
        PilotoResponseDTO piloto = pilotoService.obtenerPilotoPorId(id, managerEscuderiaId);
        return ResponseEntity.ok(piloto); 
    }

    //  ACTUALIZAR UN PILOTO
    @PutMapping("/{id}") 
    public ResponseEntity<PilotoResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody PilotoRequestDTO dto) {
        
        // Obtenemos la escudería directamente del Token JWT
        Long managerEscuderiaId = usuarioService.obtenerEscuderiaIdDelUsuarioAutenticado();
        
        PilotoResponseDTO actualizado = pilotoService.actualizarPiloto(id, dto, managerEscuderiaId);
        return ResponseEntity.ok(actualizado); 
    }

    //  ELIMINAR UN PILOTO 
    @DeleteMapping("/{id}") 
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        //  Obtenemos la escudería directamente del Token JWT
        Long managerEscuderiaId = usuarioService.obtenerEscuderiaIdDelUsuarioAutenticado();
        
        pilotoService.eliminarPiloto(id, managerEscuderiaId);
        return ResponseEntity.noContent().build(); 
    }
}