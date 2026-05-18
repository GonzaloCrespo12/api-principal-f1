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

    // Obtiene TODOS los números (Ocupados y Libres)
    @Transactional(readOnly = true)
    public List<NumeroPilotoResponseDTO> listarTodos() {
        List<NumeroPiloto> todos = numeroPilotoRepository.findAll();
        return numeroPilotoMapper.toResponseDTOList(todos);
    }

    //  Obtiene SOLO los que están libres 
    @Transactional(readOnly = true)
    public List<NumeroPilotoResponseDTO> listarDisponibles() {
        List<NumeroPiloto> disponibles = numeroPilotoRepository.findByEstaDisponibleTrue(); // Usa Query Method
        return numeroPilotoMapper.toResponseDTOList(disponibles);
    }
}