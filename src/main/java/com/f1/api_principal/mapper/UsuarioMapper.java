package com.f1.api_principal.mapper;

import com.f1.api_principal.dto.request.UsuarioCreateDTO;
import com.f1.api_principal.dto.response.UsuarioResponseDTO;
import com.f1.api_principal.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

// componentModel = "spring" - que Spring trate este mapper como un Bean inyectable (@Autowired)
@Mapper(componentModel = "spring") 
public interface UsuarioMapper {

    // De DTO a Entidad (Para guardar en BD)
    // Ignora campos que JPA o la BD autogeneran, y la escudería que se asignará según el token del usuario
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fechaRegistro", ignore = true)
    @Mapping(target = "actualizadoEn", ignore = true)
    @Mapping(target = "escuderia", ignore = true) 
    Usuario toEntity(UsuarioCreateDTO dto);

    // De Entidad a DTO (Para devolver al cliente)
    // MapStruct busca dentro del objeto escuderia el campo 'nombre'
    @Mapping(target = "nombreEscuderia", source = "escuderia.nombre")
    UsuarioResponseDTO toResponseDTO(Usuario entity);
}