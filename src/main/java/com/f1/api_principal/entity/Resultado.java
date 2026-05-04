package com.f1.api_principal.entity;

import com.f1.api_principal.entity.enums.EstadoResultado;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "resultado")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Resultado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- RELACIONES TRIPLES ---

    @ManyToOne(fetch = FetchType.LAZY) // Muchos resultados pertenecen a un piloto
    @JoinColumn(name = "piloto_id") // FK en la tabla de la BD
    private Piloto piloto;

    @ManyToOne(fetch = FetchType.LAZY) // Muchos resultados pertenecen a una carrera
    @JoinColumn(name = "carrera_id")
    private Carrera carrera;

    @ManyToOne(fetch = FetchType.LAZY) // Muchos resultados pertenecen a una escudería
    @JoinColumn(name = "escuderia_id")
    private Escuderia escuderia;

    // --- DATOS DE LA CARRERA ---

    @Column(name = "posicion_final")
    private Integer posicionFinal;

    @Column(name = "puntos_obtenidos")
    private Integer puntosObtenidos;

    @Column(name = "vueltas_completadas")
    private Integer vueltasCompletadas;

    @Enumerated(EnumType.STRING)
    private EstadoResultado estado;
}