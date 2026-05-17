// Archivo: src/main/java/com/f1/api_principal/repository/UsuarioRepository.java
package com.f1.api_principal.repository;

import com.f1.api_principal.entity.Usuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Le indica a Spring que este es un componente de acceso a datos
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    // Aquí podrías agregar búsquedas personalizadas más adelante, por ejemplo:
    Optional<Usuario> findByUsername(String username); // Método para buscar un usuario por su nombre de usuario
}
