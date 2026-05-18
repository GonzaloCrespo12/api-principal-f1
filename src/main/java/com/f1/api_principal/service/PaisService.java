package com.f1.api_principal.service;

import com.f1.api_principal.dto.response.PaisResponseDTO;
import com.f1.api_principal.entity.Pais;
import com.f1.api_principal.mapper.PaisMapper;
import com.f1.api_principal.repository.PaisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaisService {

    private final PaisRepository paisRepository;
    private final PaisMapper paisMapper;

    @Transactional(readOnly = true) // Optimización para consultas sin mutación de datos
    public List<PaisResponseDTO> listarTodos() {
        List<Pais> paises = paisRepository.findAll();
        return paisMapper.toResponseDTOList(paises);
    }
}