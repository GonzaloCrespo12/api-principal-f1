// Archivo: src/main/java/com/f1/api_principal/dto/response/NumeroPilotoResponseDTO.java
package com.f1.api_principal.dto.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NumeroPilotoResponseDTO {
    private Integer id;
    private Integer valor; // Ej: 1, 16, 44, 43
    private Boolean estaDisponible;
}