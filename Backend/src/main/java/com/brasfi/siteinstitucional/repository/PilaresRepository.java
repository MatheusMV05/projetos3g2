package com.brasfi.siteinstitucional.repository;

import com.brasfi.siteinstitucional.entity.Pilares;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PilaresRepository extends JpaRepository<Pilares, Long> {
    Optional<Pilares> findBySlug(String slug);
    boolean existsBySlug(String slug);
}
