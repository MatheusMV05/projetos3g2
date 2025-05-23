package com.brasfi.siteinstitucional.admin.repository;

import com.brasfi.siteinstitucional.admin.entity.LogAuditoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface LogAuditoriaRepository extends JpaRepository<LogAuditoria, Long> {

    Page<LogAuditoria> findByUsuarioId(Long usuarioId, Pageable pageable);

    Page<LogAuditoria> findByEntidadeAndEntidadeId(String entidade, Long entidadeId, Pageable pageable);

    Page<LogAuditoria> findByAcao(String acao, Pageable pageable);

    Page<LogAuditoria> findByDataHoraBetween(LocalDateTime inicio, LocalDateTime fim, Pageable pageable);

    @Query("SELECT l FROM LogAuditoria l WHERE " +
            "(:usuarioId IS NULL OR l.usuario.id = :usuarioId) AND " +
            "(:entidade IS NULL OR l.entidade = :entidade) AND " +
            "(:acao IS NULL OR l.acao = :acao) AND " +
            "(:inicio IS NULL OR l.dataHora >= :inicio) AND " +
            "(:fim IS NULL OR l.dataHora <= :fim)")
    Page<LogAuditoria> buscarComFiltros(
            @Param("usuarioId") Long usuarioId,
            @Param("entidade") String entidade,
            @Param("acao") String acao,
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim,
            Pageable pageable);
}