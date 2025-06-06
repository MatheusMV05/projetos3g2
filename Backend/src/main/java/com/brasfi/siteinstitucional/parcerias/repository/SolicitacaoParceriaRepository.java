package com.brasfi.siteinstitucional.parcerias.repository;

import com.brasfi.siteinstitucional.parcerias.entity.SolicitacaoParceria;
import com.brasfi.siteinstitucional.parcerias.entity.StatusSolicitacao;
import com.brasfi.siteinstitucional.parcerias.entity.TipoParceria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface SolicitacaoParceriaRepository extends JpaRepository<SolicitacaoParceria, Long> {

    @Query("SELECT s FROM SolicitacaoParceria s WHERE " +
            "(:status IS NULL OR s.status = :status) AND " +
            "(:tipoSolicitacao IS NULL OR s.tipoSolicitacao = :tipoSolicitacao) AND " +
            "(:dataInicio IS NULL OR s.dataSolicitacao >= :dataInicio) AND " +
            "(:dataFim IS NULL OR s.dataSolicitacao <= :dataFim)")
    Page<SolicitacaoParceria> buscarComFiltros(
            @Param("status") StatusSolicitacao status,
            @Param("tipoSolicitacao") TipoParceria tipoSolicitacao,
            @Param("dataInicio") LocalDateTime dataInicio,
            @Param("dataFim") LocalDateTime dataFim,
            Pageable pageable);
}