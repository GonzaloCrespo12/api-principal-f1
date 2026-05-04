// Archivo: src/main/java/com/f1/api_principal/entity/Carrera.java
package com.f1.api_principal.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity // Marca la clase como una entidad de base de datos
@Table(name = "carrera") // Nombre de la tabla en el DER
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Carrera {

    @Id // Clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Autoincremental
    private Long id;

    @Column(name = "nombre_gp", nullable = false) // Mapeo de snake_case a camelCase
    private String nombreGp;

    @Column(nullable = false)
    private String pais;

    @Column(name = "longitud_km", nullable = false)
    private Float longitudKm; // Float para coincidir con el tipo float de la BD

    private Integer vueltas;

    @Column(name = "es_sprint")
    private Boolean esSprint; // Boolean para campos bool

    private LocalDateTime fecha;

    @Column(name = "nombre_circuito", nullable = false)
    private String nombreCircuito;

    @Column(name = "creado_en", updatable = false)
    private LocalDateTime creadoEn;

    @PrePersist // Se ejecuta automáticamente al crear el registro
    protected void onCreate() {
        creadoEn = LocalDateTime.now();
    }
}
