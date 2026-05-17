// Archivo: src/main/java/com/f1/api_principal/dto/response/CarreraResponseDTO.java
package com.f1.api_principal.dto.response;

import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarreraResponseDTO {
    private Long id;
    private String nombreGp;
    private String paisNombre; // Extraemos el nombre del país para aplanar el JSON y evitar sobrecarga 
    private Float longitudKm;
    private Integer vueltas;
    private Boolean esSprint;
    private LocalDateTime fecha;
    private String nombreCircuito;
}