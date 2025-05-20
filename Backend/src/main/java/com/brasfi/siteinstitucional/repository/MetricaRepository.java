package com.brasfi.siteinstitucional.repository;

import com.brasfi.siteinstitucional.entity.Metrica;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetricaRepository extends JpaRepository<Metrica, Long> {
    Page<Metrica> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}
