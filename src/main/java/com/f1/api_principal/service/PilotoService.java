package com.f1.api_principal.service;

import com.f1.api_principal.dto.request.PilotoRequestDTO;
import com.f1.api_principal.dto.response.PilotoResponseDTO;
import com.f1.api_principal.entity.Escuderia;
import com.f1.api_principal.entity.NumeroPiloto;
import com.f1.api_principal.entity.Pais;
import com.f1.api_principal.entity.Piloto;
import com.f1.api_principal.mapper.PilotoMapper;
import com.f1.api_principal.repository.EscuderiaRepository;
import com.f1.api_principal.repository.NumeroPilotoRepository;
import com.f1.api_principal.repository.PaisRepository;
import com.f1.api_principal.repository.PilotoRepository;
import com.f1.api_principal.repository.ResultadoRepository;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service // Marca la clase como un componente de lógica de negocio en Spring 
@RequiredArgsConstructor // Lombok crea un constructor con todas las variables "final" automáticamente
public class PilotoService {

    // Inyección de dependencias: El Service necesita hablar con varios repositorios y el mapper 
    private final PilotoRepository pilotoRepository;
    private final PaisRepository paisRepository;
    private final NumeroPilotoRepository numeroPilotoRepository;
    private final EscuderiaRepository escuderiaRepository;
    private final PilotoMapper pilotoMapper;
    private final ResultadoRepository resultadoRepository;

    @Transactional // Toda esta operación es un bloque único y seguro 
    public PilotoResponseDTO crearPiloto(PilotoRequestDTO dto, Long managerEscuderiaId) {
        
        // 1. Validar que el país exista
        Pais pais = paisRepository.findById(dto.getPaisId())
                .orElseThrow(() -> new IllegalArgumentException("El país especificado no existe."));

        // 2. Regla de Negocio: Validar Dorsal
        NumeroPiloto numero = numeroPilotoRepository.findById(dto.getNumeroId())
                .orElseThrow(() -> new IllegalArgumentException("El dorsal especificado no existe."));

        if (!numero.getEstaDisponible()) {
            throw new IllegalArgumentException("El dorsal " + numero.getValor() + " ya está en uso en la parrilla.");
        }

        // 3. Aislamiento: Obtener la escudería a la que pertenece el mánager
        Escuderia escuderia = escuderiaRepository.findById(managerEscuderiaId)
                .orElseThrow(() -> new IllegalArgumentException("La escudería del mánager no existe."));

        // 4. Transformar el DTO a Entidad usando nuestro Mapper mágico
        Piloto piloto = pilotoMapper.toEntity(dto);
        
        // 5. Asignar las relaciones que el Mapper ignoró a propósito
        piloto.setPais(pais);
        piloto.setNumero(numero);
        piloto.setEscuderia(escuderia);

        // 6. Actualizar el estado del dorsal para que nadie más lo use
        numero.setEstaDisponible(false);
        numeroPilotoRepository.save(numero);

        // 7. Guardar en Base de Datos y retornar el DTO de respuesta
        Piloto pilotoGuardado = pilotoRepository.save(piloto);
        return pilotoMapper.toResponseDTO(pilotoGuardado);
    }
    @Transactional
    public void eliminarPiloto(Long id, Long managerEscuderiaId) {
        
        // 1. Aislamiento SaaS: Buscar el piloto asegurando que pertenezca a la escudería del mánager
        Piloto piloto = pilotoRepository.findByIdAndEscuderiaId(id, managerEscuderiaId)
                .orElseThrow(() -> new IllegalArgumentException("El piloto no existe o no pertenece a tu escudería."));

        // 2. Consultar si el piloto tiene registros asociados en la tabla Resultado
        // Spring Data JPA genera este método automáticamente si usas la convención de nombres
        boolean tieneHistorial = resultadoRepository.existsByPilotoId(id);

        if (tieneHistorial) {
            // REGLA: Borrado Lógico (Soft Delete)
            piloto.setEstado(false);
            pilotoRepository.save(piloto);
            // Nota pragmática: No liberamos el dorsal, porque el piloto sigue existiendo históricamente con ese número
        } else {
            // REGLA: Borrado Físico (Hard Delete) - Fue creado por error
            
            // Acción Pragmática: Como se va a borrar del todo, liberamos su dorsal para que otro piloto pueda usarlo
            if (piloto.getNumero() != null) {
                NumeroPiloto numero = piloto.getNumero();
                numero.setEstaDisponible(true);
                numeroPilotoRepository.save(numero);
            }
            
            pilotoRepository.delete(piloto);
        }
    }

    // 1. CONSULTAR TODOS LOS PILOTOS DE LA ESCUDERÍA [ci
    @Transactional(readOnly = true) // readOnly optimiza el rendimiento en consultas de solo lectura
    public List<PilotoResponseDTO> obtenerPilotosPorEscuderia(Long managerEscuderiaId) {
        // Buscamos solo los pilotos que pertenecen a esta escudería y que estén activos (estado = true)
        List<Piloto> pilotos = pilotoRepository.findAllByEscuderiaIdAndEstadoTrue(managerEscuderiaId);
        
        // Convertimos la lista de Entidades a una lista de DTOs de respuesta usando Java 21 Streams 
        return pilotos.stream()
                .map(pilotoMapper::toResponseDTO)
                .toList();
    }

    // 2. CONSULTAR UN PILOTO ESPECÍFICO POR ID 
    @Transactional(readOnly = true)
    public PilotoResponseDTO obtenerPilotoPorId(Long id, Long managerEscuderiaId) {
        // Buscamos asegurando el aislamiento: ID del piloto + ID de la escudería del mánager
        Piloto piloto = pilotoRepository.findByIdAndEscuderiaId(id, managerEscuderiaId)
                .orElseThrow(() -> new IllegalArgumentException("Piloto no encontrado o no pertenece a tu escudería."));
        
        return pilotoMapper.toResponseDTO(piloto);
    }

    // 3. ACTUALIZAR UN PILOTO EXISTENTE 
    @Transactional
    public PilotoResponseDTO actualizarPiloto(Long id, PilotoRequestDTO dto, Long managerEscuderiaId) {
        // Aislamiento: Primero verificamos que el piloto exista y sea de nuestra escudería
        Piloto piloto = pilotoRepository.findByIdAndEscuderiaId(id, managerEscuderiaId)
                .orElseThrow(() -> new IllegalArgumentException("Piloto no encontrado o no pertenece a tu escudería."));

        // Validamos y actualizamos el País si cambió 
        if (!piloto.getPais().getId().equals(dto.getPaisId())) {
            Pais nuevoPais = paisRepository.findById(dto.getPaisId())
                    .orElseThrow(() -> new IllegalArgumentException("El nuevo país especificado no existe."));
            piloto.setPais(nuevoPais);
        }

        // Lógica compleja para el Dorsal (Número): ¿Cambió el número de piloto? 
        if (!piloto.getNumero().getId().equals(dto.getNumeroId())) {
            // 1. Buscar el nuevo dorsal solicitado
            NumeroPiloto nuevoNumero = numeroPilotoRepository.findById(dto.getNumeroId())
                    .orElseThrow(() -> new IllegalArgumentException("El dorsal especificado no existe."));

            // 2. Validar que esté libre
            if (!nuevoNumero.getEstaDisponible()) {
                throw new IllegalArgumentException("El dorsal " + nuevoNumero.getValor() + " ya está ocupado.");
            }

            // 3. Liberar el dorsal viejo que tenía el piloto
            NumeroPiloto numeroViejo = piloto.getNumero();
            numeroViejo.setEstaDisponible(true);
            numeroPilotoRepository.save(numeroViejo);

            // 4. Bloquear el nuevo dorsal y asignárselo
            nuevoNumero.setEstaDisponible(false);
            numeroPilotoRepository.save(nuevoNumero);
            piloto.setNumero(nuevoNumero);
        }

        // Actualizamos los campos básicos directamente 
        piloto.setNombre(dto.getNombre());
        piloto.setSiglas(dto.getSiglas());
        piloto.setRol(com.f1.api_principal.entity.enums.RolPiloto.valueOf(dto.getRol())); // Convierte el String del DTO al Enum de la entidad
        piloto.setPrioridad(dto.getPrioridad());

        // Guardamos los cambios. El @PreUpdate se encargará de actualizar la fecha de modificación de forma automática.
        Piloto pilotoActualizado = pilotoRepository.save(piloto);
        return pilotoMapper.toResponseDTO(pilotoActualizado);
    }
}