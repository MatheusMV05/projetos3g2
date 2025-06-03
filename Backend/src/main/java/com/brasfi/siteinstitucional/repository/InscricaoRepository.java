package com.brasfi.siteinstitucional.repository;

import com.brasfi.siteinstitucional.entity.Inscricao;
import com.brasfi.siteinstitucional.entity.Evento;
import com.brasfi.siteinstitucional.entity.InscricaoStatus; // Importe o InscricaoStatus
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InscricaoRepository extends JpaRepository<Inscricao, Long> {
    boolean existsByEventoIdAndEmail(Long eventoId, String email);
    long countByEventoIdAndStatus(Long eventoId, InscricaoStatus status);
    List<Inscricao> findByEvento(Evento evento);

    // Novo método: Encontra inscrições por evento e status
    List<Inscricao> findByEventoAndStatus(Evento evento, InscricaoStatus status);
}