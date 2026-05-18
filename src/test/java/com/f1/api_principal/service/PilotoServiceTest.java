// Archivo: src/test/java/com/f1/api_principal/service/PilotoServiceTest.java
package com.f1.api_principal.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.f1.api_principal.dto.request.PilotoRequestDTO;
import com.f1.api_principal.dto.response.PilotoResponseDTO;
import com.f1.api_principal.entity.Escuderia;
import com.f1.api_principal.entity.NumeroPiloto;
import com.f1.api_principal.entity.Pais;
import com.f1.api_principal.entity.Piloto;
import com.f1.api_principal.mapper.PilotoMapper; // <- Asegúrate de que este import exista
import com.f1.api_principal.repository.EscuderiaRepository;
import com.f1.api_principal.repository.NumeroPilotoRepository;
import com.f1.api_principal.repository.PaisRepository;
import com.f1.api_principal.repository.PilotoRepository;
import com.f1.api_principal.repository.ResultadoRepository;

@ExtendWith(MockitoExtension.class) 
public class PilotoServiceTest {

    @Mock
    private PilotoRepository pilotoRepository;

    @Mock
    private EscuderiaRepository escuderiaRepository;

    @Mock
    private PaisRepository paisRepository; 

    @Mock
    private NumeroPilotoRepository numeroPilotoRepository; 

    @Mock
    private PilotoMapper pilotoMapper; 

    @Mock
    private ResultadoRepository resultadoRepository;

    @InjectMocks
    private PilotoService pilotoService; 

    private PilotoRequestDTO requestDTO;
    private Piloto pilotoEntity;
    private Escuderia escuderiaEntity;
    private Pais paisEntity;
    private NumeroPiloto numeroEntity;
    private PilotoResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        requestDTO = new PilotoRequestDTO();
        requestDTO.setNombre("Charles Leclerc");
        requestDTO.setSiglas("LEC");
        requestDTO.setPaisId(1);
        requestDTO.setNumeroId(16);
        requestDTO.setRol("TITULAR");
        requestDTO.setPrioridad(1);

        paisEntity = new Pais();
        paisEntity.setId(1);
        paisEntity.setNombre("Mónaco");

        numeroEntity = new NumeroPiloto();
        numeroEntity.setId(16);
        numeroEntity.setValor(16);
        numeroEntity.setEstaDisponible(true); 

        escuderiaEntity = new Escuderia();
        escuderiaEntity.setId(2L);
        escuderiaEntity.setNombre("Ferrari");

        pilotoEntity = new Piloto();
        pilotoEntity.setId(1L);
        pilotoEntity.setNombre("Charles Leclerc");
        pilotoEntity.setSiglas("LEC");
        pilotoEntity.setPais(paisEntity);
        pilotoEntity.setNumero(numeroEntity);
        pilotoEntity.setEscuderia(escuderiaEntity);

        responseDTO = new PilotoResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setNombre("Charles Leclerc");
        responseDTO.setSiglas("LEC");
        responseDTO.setNumeroValor(16);
    }

    @Test
    public void testCrearPiloto_Exito() {
        // --- ARRANGE (Simular el comportamiento de los objetos falsos) ---
        
        // 1. Cuando el service use el mapper para convertir de DTO a Entidad, devolvemos el pilotoEntity
        when(pilotoMapper.toEntity(any(PilotoRequestDTO.class))).thenReturn(pilotoEntity);

        // 2. Simulamos las búsquedas en la base de datos
        when(paisRepository.findById(1)).thenReturn(Optional.of(paisEntity));
        when(numeroPilotoRepository.findById(16)).thenReturn(Optional.of(numeroEntity));
        when(escuderiaRepository.findById(2L)).thenReturn(Optional.of(escuderiaEntity));

        // 3. Cuando el repositorio guarde el piloto, devolvemos el mismo piloto guardado
        when(pilotoRepository.save(any(Piloto.class))).thenReturn(pilotoEntity);

        // 4. Cuando el service use el mapper para convertir la Entidad guardada al DTO de respuesta, devolvemos responseDTO

        when(pilotoMapper.toResponseDTO(any(Piloto.class))).thenReturn(responseDTO);

        // --- ACT (Ejecutar el método real) ---
        PilotoResponseDTO resultado = pilotoService.crearPiloto(requestDTO, 2L); 

        // --- ASSERT (Comprobar resultados) ---
        assertNotNull(resultado, "El resultado no debería ser nulo");
        assertEquals("Charles Leclerc", resultado.getNombre(), "El nombre debe coincidir");
    }

    @Test
    public void testEliminarPiloto_FallaSiTieneResultados() {
        // 1. Arrange (Preparar)
        Long pilotoId = 1L;
        Long escuderiaId = 2L; 
        
        lenient().when(pilotoRepository.findById(pilotoId)).thenReturn(Optional.of(pilotoEntity));
        lenient().when(resultadoRepository.existsByPilotoId(pilotoId)).thenReturn(true);

        // (Ejecutar y comprobar la excepción)
        assertThrows(RuntimeException.class, () -> {
            pilotoService.eliminarPiloto(pilotoId, escuderiaId); 
        });
    }
}