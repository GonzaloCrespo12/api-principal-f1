package com.f1.api_principal.mapper;

import com.f1.api_principal.dto.response.PaisResponseDTO;
import com.f1.api_principal.entity.Pais;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PaisMapper {

    PaisResponseDTO toResponseDTO(Pais pais);

    List<PaisResponseDTO> toResponseDTOList(List<Pais> paises);
}