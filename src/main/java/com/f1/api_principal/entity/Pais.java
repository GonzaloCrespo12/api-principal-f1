package com.f1.api_principal.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pais")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pais {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // Usamos Integer porque el volumen de países es pequeño

    @Column(nullable = false, unique = true)
    private String nombre;
}