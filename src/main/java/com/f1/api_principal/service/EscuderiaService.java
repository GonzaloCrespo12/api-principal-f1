// Archivo: src/main/java/com/f1/api_principal/service/EscuderiaService.java
package com.f1.api_principal.service;

import com.f1.api_principal.dto.response.EscuderiaResponseDTO;
import com.f1.api_principal.entity.Escuderia;
import com.f1.api_principal.mapper.EscuderiaMapper;
import com.f1.api_principal.repository.EscuderiaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class EscuderiaService {

    private final EscuderiaRepository escuderiaRepository;
    private final EscuderiaMapper escuderiaMapper;

    public EscuderiaService(EscuderiaRepository escuderiaRepository, EscuderiaMapper escuderiaMapper) {
        this.escuderiaRepository = escuderiaRepository;
        this.escuderiaMapper = escuderiaMapper;
    }

    // Listar todas [cite: 739]
    public List<EscuderiaResponseDTO> obtenerTodas() {
        return escuderiaRepository.findAll()
                .stream()
                .map(escuderiaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Buscar por ID [cite: 741, 927-929]
    public EscuderiaResponseDTO obtenerPorId(Long id) {
        Escuderia escuderia = escuderiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Escudería no encontrada con ID: " + id));
        return escuderiaMapper.toResponseDTO(escuderia);
    }
}