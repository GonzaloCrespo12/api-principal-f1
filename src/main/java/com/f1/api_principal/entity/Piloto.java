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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pais_id")
    private Pais pais;

    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "numero_id", unique = true)
    private NumeroPiloto numero;

    @Enumerated(EnumType.STRING)
    private RolPiloto rol;

    private Integer prioridad;

    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "escuderia_id") 
    private Escuderia escuderia;

    @Column(name = "estado", columnDefinition = "boolean default true")
    private Boolean estado = true; // Por defecto un piloto nace "activo"

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