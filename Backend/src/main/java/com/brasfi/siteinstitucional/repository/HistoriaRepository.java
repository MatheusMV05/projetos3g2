package com.brasfi.siteinstitucional.repository;

import com.brasfi.siteinstitucional.entity.Historia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HistoriaRepository extends JpaRepository<Historia, Long> {
    Optional<Historia> findBySlug(String slug);
    boolean existsBySlug(String slug);
}
