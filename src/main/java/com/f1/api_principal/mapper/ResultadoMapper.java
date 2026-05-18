package com.f1.api_principal.mapper;

import com.f1.api_principal.dto.response.ResultadoResponseDTO;
import com.f1.api_principal.entity.Resultado;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ResultadoMapper {

    @Mapping(source = "carrera.nombreGp", target = "carreraNombreGp")
    @Mapping(source = "piloto.nombre", target = "pilotoNombre")
    @Mapping(source = "escuderia.nombre", target = "escuderiaNombre")
    @Mapping(source = "estado", target = "estadoResultado") // Mapea Enum de estado a String
    ResultadoResponseDTO toResponseDTO(Resultado resultado);
}