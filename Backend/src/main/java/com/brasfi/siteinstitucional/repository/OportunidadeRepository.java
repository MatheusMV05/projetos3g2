package com.brasfi.siteinstitucional.repository;

import com.brasfi.siteinstitucional.entity.Oportunidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OportunidadeRepository extends JpaRepository<Oportunidade, Long> {
    // Métodos CRUD básicos já vêm do JpaRepository

    // Exemplo de método de busca personalizado
    List<Oportunidade> findByTituloContainingIgnoreCase(String titulo);
}