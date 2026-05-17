// Archivo: src/main/java/com/f1/api_principal/dto/response/ResultadoResponseDTO.java
package com.f1.api_principal.dto.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultadoResponseDTO {
    private Long id;
    private String carreraNombreGp;     // Nombre del Gran Premio (ej: GP de Mónaco)
    private String pilotoNombre;        // Nombre del piloto que corrió
    private String escuderiaNombre;     // Escudería con la que corrió en ese momento
    private Integer posicionFinal;      // Posición en la que cruzó la meta
    private Integer puntosObtenidos;    // Puntos que aportó al campeonato
    private Integer vueltasCompletadas; 
    private String estadoResultado;     
}