package com.f1.api_principal.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UsuarioResponseDTO {
    
    private Long id;
    private String username;
    private LocalDateTime fechaRegistro;
    
    // Podemos devolver el nombre de la escudería para que el frontend lo muestre fácilmente
    private String nombreEscuderia;
}