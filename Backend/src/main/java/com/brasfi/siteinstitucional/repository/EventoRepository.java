package com.brasfi.siteinstitucional.repository;

import com.brasfi.siteinstitucional.entity.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
    // Métodos CRUD básicos já vêm do JpaRepository (save, findById, findAll, deleteById)

    // Exemplo de método de busca personalizado (opcional)
    List<Evento> findByTituloContainingIgnoreCase(String titulo);
    List<Evento> findByLocalContainingIgnoreCase(String local);
}