package com.f1.api_principal.service;

import com.f1.api_principal.dto.response.ResultadoResponseDTO;
import com.f1.api_principal.mapper.ResultadoMapper;
import com.f1.api_principal.repository.ResultadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResultadoService {

    private final ResultadoRepository resultadoRepository;
    private final ResultadoMapper resultadoMapper;

    @Transactional(readOnly = true) // Optimiza la velocidad al ser solo lectura 
    public List<ResultadoResponseDTO> obtenerHistorialPorEscuderia(Long managerEscuderiaId) {
        return resultadoRepository.findAllByEscuderiaId(managerEscuderiaId).stream()
                .map(resultadoMapper::toResponseDTO)
                .toList();
    }
}