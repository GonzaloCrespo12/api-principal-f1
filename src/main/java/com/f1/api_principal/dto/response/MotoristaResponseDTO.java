package com.f1.api_principal.dto.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MotoristaResponseDTO {
    private Long id;
    private String nombre;
}