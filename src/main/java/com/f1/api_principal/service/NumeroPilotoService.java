// Archivo: src/main/java/com/f1/api_principal/service/NumeroPilotoService.java
package com.f1.api_principal.service;

import com.f1.api_principal.dto.response.NumeroPilotoResponseDTO;
import com.f1.api_principal.entity.NumeroPiloto;
import com.f1.api_principal.mapper.NumeroPilotoMapper;
import com.f1.api_principal.repository.NumeroPilotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NumeroPilotoService {

    private final NumeroPilotoRepository numeroPilotoRepository;
    private final NumeroPilotoMapper numeroPilotoMapper;

    // 1. Obtiene TODOS los números (Ocupados y Libres)
    @Transactional(readOnly = true)
    public List<NumeroPilotoResponseDTO> listarTodos() {
        List<NumeroPiloto> todos = numeroPilotoRepository.findAll();
        return numeroPilotoMapper.toResponseDTOList(todos);
    }

    // 2. Obtiene SOLO los que están libres (Ideal para el formulario de crear Piloto)
    @Transactional(readOnly = true)
    public List<NumeroPilotoResponseDTO> listarDisponibles() {
        List<NumeroPiloto> disponibles = numeroPilotoRepository.findByEstaDisponibleTrue(); // Usa tu Query Method
        return numeroPilotoMapper.toResponseDTOList(disponibles);
    }
}