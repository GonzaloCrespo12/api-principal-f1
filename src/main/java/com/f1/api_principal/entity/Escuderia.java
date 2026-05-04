// Archivo: src/main/java/com/f1/api_principal/entity/Escuderia.java
package com.f1.api_principal.entity;

import com.f1.api_principal.entity.enums.TipoMotorista;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity // Marca la clase como entidad gestionada por JPA
@Table(name = "escuderia") // Define el nombre exacto de la tabla
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Escuderia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) // NN (Not Null) según tu DER
    private String nombre;

    @Column(name = "pais_origen") // Vincula la variable camelCase con la columna snake_case
    private String paisOrigen;

    @Column(name = "jefe_equipo")
    private String jefeEquipo;

    @Column(name = "anio_fundacion")
    private Integer anioFundacion; // Usamos la clase Integer en lugar del tipo primitivo int

    @ManyToOne(fetch = FetchType.LAZY) // Muchas escuderías pueden usar un motorista
    @JoinColumn(name = "motorista_id") 
    private Motorista motorista;

    @Enumerated(EnumType.STRING) // Poder agrupar los enums de manera correcta
    @Column(name = "tipo_motorista")
    private TipoMotorista tipoMotorista;

    @Column(name = "creado_en", updatable = false)
    private LocalDateTime creadoEn;

    @Column(name = "actualizado_en")
    private LocalDateTime actualizadoEn;

    @PrePersist
    protected void onCreate() {
        creadoEn = LocalDateTime.now();
        actualizadoEn = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        actualizadoEn = LocalDateTime.now();
    }
}