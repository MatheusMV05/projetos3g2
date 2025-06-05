package com.brasfi.repository;

import com.brasfi.siteinstitucional.auth.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("SELECT u.id FROM Usuario u JOIN u.perfis p WHERE p.nome = 'ADMIN'")
    List<Long> findAdminIds();

    @Query("SELECT u.id FROM Usuario u WHERE u.ativo = true")
    List<Long> findAllIds();

    @Query("SELECT u FROM Usuario u WHERE u.ativo = true ORDER BY u.nome ASC")
    List<Usuario> findAllActiveUsers();
}