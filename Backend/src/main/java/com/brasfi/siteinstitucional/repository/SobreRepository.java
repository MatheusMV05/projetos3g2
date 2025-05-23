package com.brasfi.siteinstitucional.repository;

import com.brasfi.siteinstitucional.entity.Sobre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SobreRepository extends JpaRepository<Sobre, Long> {
    Optional<Sobre> findBySlug(String slug);
    boolean existsBySlug(String slug);
}
