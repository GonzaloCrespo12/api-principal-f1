package com.f1.api_principal.repository;

import com.f1.api_principal.entity.Resultado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultadoRepository extends JpaRepository<Resultado, Long> {
    // Spring leerá este nombre y armará el SQL automáticamente: 
    // SELECT COUNT(*) > 0 FROM resultado WHERE piloto_id = ?
    boolean existsByPilotoId(Long pilotoId);
    // Para eliminar resultados asociados a un piloto específico
    java.util.List<com.f1.api_principal.entity.Resultado> findAllByEscuderiaId(Long escuderiaId);
}