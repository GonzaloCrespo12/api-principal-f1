// Archivo: src/main/java/com/f1/api_principal/mapper/PilotoMapper.java
package com.f1.api_principal.mapper;

import com.f1.api_principal.dto.request.PilotoRequestDTO;
import com.f1.api_principal.dto.response.PilotoResponseDTO;
import com.f1.api_principal.entity.Piloto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring") // Indica que es un componente de Spring y se puede inyectar
public interface PilotoMapper {

    // 1. Convertir de Entidad a DTO de Respuesta (Hacia el cliente)
    @Mapping(source = "pais.nombre", target = "paisNombre")
    @Mapping(source = "numero.valor", target = "numeroValor")
    PilotoResponseDTO toResponseDTO(Piloto piloto);

    // 2. Convertir de RequestDTO a Entidad (Desde el cliente para guardar en BD)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pais", ignore = true)      // Se resolverán en la capa Service
    @Mapping(target = "numero", ignore = true)    // Se resolverán en la capa Service
    @Mapping(target = "escuderia", ignore = true) // Se resolverá mediante el Token
    @Mapping(target = "creadoEn", ignore = true)
    @Mapping(target = "actualizadoEn", ignore = true) // Por defecto es true, no se setea desde el DTO
    @Mapping(target = "estado", ignore = true) // Por defecto es true, no se setea desde el DTO
    Piloto toEntity(PilotoRequestDTO dto);
}