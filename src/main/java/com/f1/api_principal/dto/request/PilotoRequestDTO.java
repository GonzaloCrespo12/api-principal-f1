package com.f1.api_principal.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PilotoRequestDTO {

    @NotBlank(message = "El nombre del piloto no puede estar vacío")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String nombre;

    @NotBlank(message = "Las siglas son obligatorias")
    @Size(min = 3, max = 3, message = "Las siglas deben tener exactamente 3 letras")
    private String siglas;

    @NotNull(message = "El ID del país es obligatorio")
    private Integer paisId;

    @NotNull(message = "El ID del dorsal (número) es obligatorio")
    private Integer numeroId;

    @NotBlank(message = "El rol del piloto es obligatorio")
    private String rol; 

    @NotNull(message = "La prioridad es obligatoria")
    @Min(value = 1, message = "La prioridad mínima es 1")
    @Max(value = 3, message = "La prioridad máxima es 3")
    private Integer prioridad;
    
    // No se incluye escuderiaId porque la obtendremos del token del Team Principal.
    // No se incluye estado porque por defecto al crear es true.
}