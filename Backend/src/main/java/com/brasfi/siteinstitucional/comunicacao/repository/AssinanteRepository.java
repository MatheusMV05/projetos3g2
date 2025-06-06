package com.brasfi.siteinstitucional.comunicacao.repository;

import com.brasfi.siteinstitucional.comunicacao.entity.Assinante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssinanteRepository extends JpaRepository<Assinante, Long> {

    Optional<Assinante> findByEmail(String email);

    boolean existsByEmail(String email);

    Page<Assinante> findByAtivo(boolean ativo, Pageable pageable);
}