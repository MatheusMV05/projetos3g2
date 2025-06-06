package com.brasfi.siteinstitucional.auth.repository;

import com.brasfi.siteinstitucional.auth.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);

    @Query("SELECT u.id FROM Usuario u JOIN u.perfis p WHERE p.nome = 'ADMIN'")
    List<Long> findAdminIds();

    @Query("SELECT u.id FROM Usuario u WHERE u.ativo = true")
    List<Long> findAllIds();
}