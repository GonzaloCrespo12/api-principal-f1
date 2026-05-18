package com.f1.api_principal.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.f1.api_principal.dto.UsuarioLoginDTO;
import com.f1.api_principal.dto.request.UsuarioCreateDTO;
import com.f1.api_principal.dto.response.UsuarioResponseDTO;
import com.f1.api_principal.entity.Escuderia;
import com.f1.api_principal.entity.Usuario;
import com.f1.api_principal.mapper.UsuarioMapper;
import com.f1.api_principal.repository.EscuderiaRepository;
import com.f1.api_principal.repository.UsuarioRepository;
import com.f1.api_principal.security.JwtUtil;

@Service // Marca la clase como componente de Spring que contiene lógica de negocio
public class UsuarioService {

    // Declaramos las dependencias
    private final UsuarioRepository usuarioRepository;
    private final EscuderiaRepository escuderiaRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // Inyección por constructor (la forma recomendada por tu curso)
    public UsuarioService(UsuarioRepository usuarioRepository, 
                        EscuderiaRepository escuderiaRepository,
                        UsuarioMapper usuarioMapper, 
                        PasswordEncoder passwordEncoder,
                        JwtUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository;
        this.escuderiaRepository = escuderiaRepository;
        this.usuarioMapper = usuarioMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Transactional // Si algo falla a la mitad, se revierte toda la operación en la BD
    public UsuarioResponseDTO registrarUsuario(UsuarioCreateDTO dto) {
        
        // 1. Validar que el username no exista (evitamos duplicados)
        if (usuarioRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new RuntimeException("El username ya está en uso"); 
        }

        // 2. Convertir el DTO a Entidad
        Usuario nuevoUsuario = usuarioMapper.toEntity(dto);

        // 3. Encriptar la contraseña (¡Crítico!)
        nuevoUsuario.setPassword(passwordEncoder.encode(dto.getPassword()));

        // 4. Asignar escudería si el usuario envió el ID
        if (dto.getEscuderiaId() != null) {
            Escuderia escuderia = escuderiaRepository.findById(dto.getEscuderiaId())
                .orElseThrow(() -> new RuntimeException("La escudería no existe"));
            nuevoUsuario.setEscuderia(escuderia);
        }

        // 5. Guardar en BD y devolver el DTO de respuesta
        Usuario usuarioGuardado = usuarioRepository.save(nuevoUsuario);
        return usuarioMapper.toResponseDTO(usuarioGuardado);
    }

    public String login(UsuarioLoginDTO dto) {
        // 1. Buscamos al usuario en la base de datos por su username
        Usuario usuario = usuarioRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario o contraseña incorrectos"));

        // 2. Comparamos la contraseña enviada con el hash encriptado de la BD
        if (!passwordEncoder.matches(dto.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Usuario o contraseña incorrectos");
        }

        // 3. Si todo está perfecto, la máquina fabrica y le entrega su Pase VIP (Token)
        return jwtUtil.generarToken(usuario.getUsername());
    }

    // Agrega este método al final de tu clase
    public Long obtenerEscuderiaIdDelUsuarioAutenticado() {
        // 1. El guardia (Filtro JWT) guardó el nombre del usuario en el contexto de seguridad. Lo sacamos:
        String username = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getName();
        
        // 2. Buscamos a ese usuario (ej. fred_vasseur) en la base de datos
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario autenticado no encontrado en BD"));
                
        // 3. Devolvemos el ID de su escudería (ej. 2 para Ferrari)
        return usuario.getEscuderia().getId();
    }
}