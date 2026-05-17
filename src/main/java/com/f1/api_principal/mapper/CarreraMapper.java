// Archivo: src/main/java/com/f1/api_principal/mapper/CarreraMapper.java
package com.f1.api_principal.mapper;

import com.f1.api_principal.dto.response.CarreraResponseDTO;
import com.f1.api_principal.entity.Carrera;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring") // Se registra como bean de Spring para poder inyectarlo [cite: 1418]
public interface CarreraMapper {

    // Mapea el atributo nombre dentro de la entidad Pais hacia paisNombre en el DTO [cite: 1444-1445]
    @Mapping(source = "pais.nombre", target = "paisNombre")
    CarreraResponseDTO toResponseDTO(Carrera carrera);

    // Mapea automáticamente colecciones enteras recorriendo la lista [cite: 1408-1410]
    List<CarreraResponseDTO> toResponseDTOList(List<Carrera> carreras);
}