// Archivo: src/main/java/com/f1/api_principal/mapper/NumeroPilotoMapper.java
package com.f1.api_principal.mapper;

import com.f1.api_principal.dto.response.NumeroPilotoResponseDTO;
import com.f1.api_principal.entity.NumeroPiloto;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface NumeroPilotoMapper {

    NumeroPilotoResponseDTO toResponseDTO(NumeroPiloto numeroPiloto);

    List<NumeroPilotoResponseDTO> toResponseDTOList(List<NumeroPiloto> numeros);
}