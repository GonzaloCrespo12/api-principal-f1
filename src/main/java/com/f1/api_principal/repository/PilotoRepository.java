package com.f1.api_principal.repository;

import com.f1.api_principal.entity.Piloto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PilotoRepository extends JpaRepository<Piloto, Long> {

    // Devuelve todos los pilotos activos de una escudería específica
    List<Piloto> findAllByEscuderiaIdAndEstadoTrue(Long escuderiaId);

    // Busca un piloto por su ID, asegurando que PERTENEZCA a la escudería del Mánager
    Optional<Piloto> findByIdAndEscuderiaId(Long id, Long escuderiaId);
    
}