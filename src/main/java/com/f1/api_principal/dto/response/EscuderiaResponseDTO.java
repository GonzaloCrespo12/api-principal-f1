package com.f1.api_principal.dto.response;

import lombok.Data;

@Data
public class EscuderiaResponseDTO {
    private Long id;
    private String nombre;
    private String jefeEquipo;
    private Integer anioFundacion;
    private String tipoMotorista;
    
    // Aplanamos las relaciones para entregar texto limpio al cliente
    private String nombrePais;
    private String nombreMotorista;
}