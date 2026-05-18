package com.f1.api_principal.service;

import com.f1.api_principal.dto.response.CarreraResponseDTO;
import com.f1.api_principal.entity.Carrera;
import com.f1.api_principal.mapper.CarreraMapper;
import com.f1.api_principal.repository.CarreraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service // Registra la clase en el contenedor de Spring 
@RequiredArgsConstructor // Inyecta las dependencias finales por constructor 
public class CarreraService {

    private final CarreraRepository carreraRepository;
    private final CarreraMapper carreraMapper;

    @Transactional(readOnly = true)
    public List<CarreraResponseDTO> obtenerCalendario(Boolean esSprint) { //  el parámetro esSprint es opcional, puede ser null
        List<Carrera> carreras;
        
        if (esSprint == null) {
            carreras = carreraRepository.findAll(); // Si no hay filtro, trae todas
        } else {
            carreras = carreraRepository.findByEsSprint(esSprint); // Si hay filtro, busca por Sprint
        }
        
        return carreraMapper.toResponseDTOList(carreras);
    }
}