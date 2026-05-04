// Archivo: src/main/java/com/f1/api_principal/repository/CarreraRepository.java
package com.f1.api_principal.repository;

import com.f1.api_principal.entity.Carrera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Marca la interfaz como un repositorio de Spring[cite: 2]
public interface CarreraRepository extends JpaRepository<Carrera, Long> {
}
