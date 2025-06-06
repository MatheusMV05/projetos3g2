package com.brasfi.siteinstitucional.comunicacao.repository;

import com.brasfi.siteinstitucional.comunicacao.entity.Mensagem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface MensagemRepository extends JpaRepository<Mensagem, Long> {

    Page<Mensagem> findByLida(boolean lida, Pageable pageable);

    Page<Mensagem> findByRespondida(boolean respondida, Pageable pageable);

    @Query("SELECT m FROM Mensagem m WHERE " +
            "(:lida IS NULL OR m.lida = :lida) AND " +
            "(:respondida IS NULL OR m.respondida = :respondida) AND " +
            "(:inicio IS NULL OR m.dataCriacao >= :inicio) AND " +
            "(:fim IS NULL OR m.dataCriacao <= :fim)")
    Page<Mensagem> buscarComFiltros(
            @Param("lida") Boolean lida,
            @Param("respondida") Boolean respondida,
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim,
            Pageable pageable);
}