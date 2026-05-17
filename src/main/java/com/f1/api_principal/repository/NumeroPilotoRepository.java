package com.f1.api_principal.repository;

import com.f1.api_principal.entity.NumeroPiloto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NumeroPilotoRepository extends JpaRepository<NumeroPiloto, Integer> {
    
    // Este método te servirá para mostrar solo los números libres en el frontend
    List<NumeroPiloto> findByEstaDisponibleTrue();
}