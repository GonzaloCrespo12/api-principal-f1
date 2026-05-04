// Archivo: src/main/java/com/f1/api_principal/repository/UsuarioRepository.java
package com.f1.api_principal.repository;

import com.f1.api_principal.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Le indica a Spring que este es un componente de acceso a datos
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    // Aquí podrías agregar búsquedas personalizadas más adelante, por ejemplo:
    // <Usuario> findByUsername(String username);
}
