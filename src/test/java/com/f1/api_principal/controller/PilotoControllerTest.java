// Archivo: src/test/java/com/f1/api_principal/controller/PilotoControllerTest.java
package com.f1.api_principal.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.f1.api_principal.dto.request.PilotoRequestDTO;
import com.f1.api_principal.dto.response.PilotoResponseDTO;
import com.f1.api_principal.service.PilotoService;
import com.f1.api_principal.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc // Configura el simulador de peticiones HTTP (MockMvc)
public class PilotoControllerTest {

    @Autowired
    private MockMvc mockMvc; // El simulador que enviará los JSON post

    @MockitoBean
    private PilotoService pilotoService; // Simulamos el servicio para que no toque la base de datos
    @MockitoBean 
    private UsuarioService usuarioService; // Simulamos el servicio de usuarios para que no toque la base de datos ni la seguridad real

    @Autowired
    private ObjectMapper objectMapper; // Herramienta para convertir objetos Java a texto JSON

    private PilotoRequestDTO requestDTO;
    private PilotoResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        // Armamos el DTO de entrada simulado
        requestDTO = new PilotoRequestDTO();
        requestDTO.setNombre("Charles Leclerc");
        requestDTO.setSiglas("LEC");
        requestDTO.setPaisId(1);
        requestDTO.setNumeroId(16);
        requestDTO.setRol("TITULAR");
        requestDTO.setPrioridad(1);

        // Armamos el DTO de salida esperado
        responseDTO = new PilotoResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setNombre("Charles Leclerc");
        responseDTO.setSiglas("LEC");
        responseDTO.setNumeroValor(16);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER"}) 
    public void testCrearPilotoController_Exito() throws Exception {
        // Cuando el controlador pida el ID de la escudería del mánager, devuelve 2L
        when(usuarioService.obtenerEscuderiaIdDelUsuarioAutenticado()).thenReturn(2L);
        // Arrange: Cuando el servicio reciba el DTO y cualquier ID de escudería (ej: 2L), devuelve el responseDTO
        when(pilotoService.crearPiloto(any(PilotoRequestDTO.class), eq(2L))).thenReturn(responseDTO);

        // Act & Assert: Simulamos una petición POST real a tu endpoint
        // NOTA: Cambia "/api/pilotos" por la URL exacta que pusiste en tu PilotoController
        mockMvc.perform(post("/api/pilotos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO))) // Convierte el requestDTO a JSON de texto
                .andExpect(status().isCreated()) // Esperamos un código HTTP 201 Created
                .andExpect(jsonPath("$.nombre").value("Charles Leclerc")) // Verificamos que el JSON de respuesta tenga el nombre correcto
                .andExpect(jsonPath("$.numeroValor").value(16));
    }
}
