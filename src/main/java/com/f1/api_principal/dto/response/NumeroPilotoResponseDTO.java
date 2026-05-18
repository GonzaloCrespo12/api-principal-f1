package com.f1.api_principal.dto.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NumeroPilotoResponseDTO {
    private Integer id;
    private Integer valor;
    private Boolean estaDisponible;
}