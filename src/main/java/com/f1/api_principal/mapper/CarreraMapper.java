package com.f1.api_principal.mapper;

import com.f1.api_principal.dto.response.CarreraResponseDTO;
import com.f1.api_principal.entity.Carrera;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring") // Se registra como bean de Spring para poder inyectarlo 
public interface CarreraMapper {

    // Mapea el atributo nombre dentro de la entidad Pais hacia paisNombre en el DTO 
    @Mapping(source = "pais.nombre", target = "paisNombre")
    CarreraResponseDTO toResponseDTO(Carrera carrera);

    // Mapea automáticamente colecciones enteras recorriendo la lista 
    List<CarreraResponseDTO> toResponseDTOList(List<Carrera> carreras);
}