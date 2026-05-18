package com.f1.api_principal.dto.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaisResponseDTO {
    private Integer id;
    private String nombre;
}