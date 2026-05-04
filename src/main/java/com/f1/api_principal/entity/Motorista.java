package com.f1.api_principal.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "motorista")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Motorista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre; // Ej: "Mercedes", "Ferrari", "Red Bull FORD"
}