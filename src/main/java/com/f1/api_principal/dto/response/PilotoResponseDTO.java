package com.f1.api_principal.dto.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PilotoResponseDTO {
    
    private Long id;
    private String nombre;
    private String siglas;
    
    // En lugar de devolver el objeto País completo o solo el ID, devolvemos el nombre para facilitar la lectura al cliente.
    private String paisNombre; 
    
    // En lugar de devolver el ID de la tabla NumeroPiloto, devolvemos el valor real del dorsal.
    private Integer numeroValor; 
    
    private String rol;
    private Integer prioridad;
    private Boolean estado;
}