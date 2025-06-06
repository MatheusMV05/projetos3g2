package com.brasfi.siteinstitucional.admin.scheduling.repository;

import com.brasfi.siteinstitucional.admin.scheduling.entity.PublicacaoAgendada;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface PublicacaoAgendadaRepository extends JpaRepository<PublicacaoAgendada, Long> {

    List<PublicacaoAgendada> findByPublicadoFalseAndDataPublicacaoLessThanEqual(LocalDateTime agora);

    Page<PublicacaoAgendada> findByPublicado(boolean publicado, Pageable pageable);

    Page<PublicacaoAgendada> findByUsuarioId(Long usuarioId, Pageable pageable);

    Page<PublicacaoAgendada> findByTipoConteudo(String tipoConteudo, Pageable pageable);

    @Query("SELECT p FROM PublicacaoAgendada p WHERE " +
            "(:tipoConteudo IS NULL OR p.tipoConteudo = :tipoConteudo) AND " +
            "(:publicado IS NULL OR p.publicado = :publicado) AND " +
            "(:usuarioId IS NULL OR p.usuario.id = :usuarioId) AND " +
            "(:dataInicio IS NULL OR p.dataPublicacao >= :dataInicio) AND " +
            "(:dataFim IS NULL OR p.dataPublicacao <= :dataFim)")
    Page<PublicacaoAgendada> buscarComFiltros(
            String tipoConteudo,
            Boolean publicado,
            Long usuarioId,
            LocalDateTime dataInicio,
            LocalDateTime dataFim,
            Pageable pageable);
}