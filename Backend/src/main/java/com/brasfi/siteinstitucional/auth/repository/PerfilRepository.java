package com.brasfi.siteinstitucional.auth.repository;

import com.brasfi.siteinstitucional.auth.entity.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {
    Optional<Perfil> findByNome(String nome);
}