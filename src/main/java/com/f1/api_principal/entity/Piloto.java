package com.f1.api_principal.entity;

import com.f1.api_principal.entity.enums.RolPiloto;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "piloto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Piloto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, length = 3) // Las siglas suelen ser 3 letras (ej: VER, HAM)
    private String siglas;

    private String nacionalidad;

    @Column(name = "numero_piloto")
    private Integer numeroPiloto;

    @Enumerated(EnumType.STRING)
    private RolPiloto rol;

    private Integer prioridad;

    // --- RELACIÓN ---
    @ManyToOne(fetch = FetchType.LAZY) // Muchos pilotos pertenecen a una escudería
    @JoinColumn(name = "escuderia_id") // Nombre de la FK en la tabla de la BD
    private Escuderia escuderia;

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