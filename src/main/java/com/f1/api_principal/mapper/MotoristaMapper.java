// Archivo: src/main/java/com/f1/api_principal/mapper/MotoristaMapper.java
package com.f1.api_principal.mapper;

import com.f1.api_principal.dto.response.MotoristaResponseDTO;
import com.f1.api_principal.entity.Motorista;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface MotoristaMapper {

    MotoristaResponseDTO toResponseDTO(Motorista motorista);

    List<MotoristaResponseDTO> toResponseDTOList(List<Motorista> motoristas);
}