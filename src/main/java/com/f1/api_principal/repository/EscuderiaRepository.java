// Archivo: src/main/java/com/f1/api_principal/repository/EscuderiaRepository.java
package com.f1.api_principal.repository;

import com.f1.api_principal.entity.Escuderia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EscuderiaRepository extends JpaRepository<Escuderia, Long> {
}