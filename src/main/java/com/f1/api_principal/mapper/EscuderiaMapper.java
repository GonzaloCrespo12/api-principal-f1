package com.f1.api_principal.mapper;

import com.f1.api_principal.dto.response.EscuderiaResponseDTO;
import com.f1.api_principal.entity.Escuderia;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EscuderiaMapper {

    // Extraemos el nombre del país y del motorista navegando por el objeto
    @Mapping(target = "nombrePais", source = "pais.nombre")
    @Mapping(target = "nombreMotorista", source = "motorista.nombre")
    EscuderiaResponseDTO toResponseDTO(Escuderia entity);
}