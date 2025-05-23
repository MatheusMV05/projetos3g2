package com.brasfi.siteinstitucional.repository;

import com.brasfi.siteinstitucional.entity.Depoimento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepoimentoRepository extends JpaRepository<Depoimento, Long> {
    Page<Depoimento> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}
