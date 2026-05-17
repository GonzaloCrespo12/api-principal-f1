package com.f1.api_principal.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity // Define que esta clase es una entidad JPA mapeada a una tabla
@Table(name = "usuario") // Nombre de la tabla en tu base de datos
@Data // Genera Getters, Setters, toString, etc. automáticamente (Lombok)
@NoArgsConstructor // Genera el constructor vacío que JPA necesita obligatoriamente
@AllArgsConstructor // Genera un constructor con todos los campos
public class Usuario {

    @Id // Define la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID autoincremental (típico de MySQL/PostgreSQL)
    private Long id;

    @Column(nullable = false, unique = true, length = 50) // NN (Not Null) y Único según tu DER
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "fecha_registro", updatable = false) // No permitimos que se cambie una vez creado
    private LocalDateTime fechaRegistro;

    @Column(name = "actualizado_en")
    private LocalDateTime actualizadoEn;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "escuderia_id", unique = true)
    private Escuderia escuderia;

    // Métodos automáticos para las fechas
    @PrePersist
    protected void onCreate() {
        fechaRegistro = LocalDateTime.now();
        actualizadoEn = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        actualizadoEn = LocalDateTime.now();
    }
}
