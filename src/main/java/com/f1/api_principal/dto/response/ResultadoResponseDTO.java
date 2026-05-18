package com.f1.api_principal.dto.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultadoResponseDTO {
    private Long id;
    private String carreraNombreGp;     
    private String pilotoNombre;        
    private String escuderiaNombre;     
    private Integer posicionFinal;     
    private Integer puntosObtenidos;    
    private Integer vueltasCompletadas; 
    private String estadoResultado;     
}