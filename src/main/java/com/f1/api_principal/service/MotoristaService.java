// Archivo: src/main/java/com/f1/api_principal/service/MotoristaService.java
package com.f1.api_principal.service;

import com.f1.api_principal.dto.response.MotoristaResponseDTO;
import com.f1.api_principal.entity.Motorista;
import com.f1.api_principal.mapper.MotoristaMapper;
import com.f1.api_principal.repository.MotoristaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MotoristaService {

    private final MotoristaRepository motoristaRepository;
    private final MotoristaMapper motoristaMapper;

    @Transactional(readOnly = true)
    public List<MotoristaResponseDTO> listarTodos() {
        List<Motorista> motoristas = motoristaRepository.findAll();
        return motoristaMapper.toResponseDTOList(motoristas);
    }
}