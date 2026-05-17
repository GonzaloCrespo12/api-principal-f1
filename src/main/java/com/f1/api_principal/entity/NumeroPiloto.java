package com.f1.api_principal.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "numero_piloto")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NumeroPiloto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private Integer valor; // El número real (ej: 44, 1, 16)

    @Column(name = "esta_disponible")
    private Boolean estaDisponible;
}
