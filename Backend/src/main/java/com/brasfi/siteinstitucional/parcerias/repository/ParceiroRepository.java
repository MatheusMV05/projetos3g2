package com.brasfi.siteinstitucional.parcerias.repository;

import com.brasfi.siteinstitucional.parcerias.entity.Parceiro;
import com.brasfi.siteinstitucional.parcerias.entity.TipoParceria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ParceiroRepository extends JpaRepository<Parceiro, Long> {

    List<Parceiro> findByVisivelTrueAndAtivoTrueOrderByNomeOrganizacaoAsc();

    Page<Parceiro> findByAtivoTrue(Pageable pageable);

    @Query("SELECT p FROM Parceiro p LEFT JOIN p.categorias cat WHERE " +
            "(:nome IS NULL OR LOWER(p.nomeOrganizacao) LIKE LOWER(CONCAT('%', :nome, '%'))) AND " +
            "(:tipo IS NULL OR p.tipoParceria = :tipo) AND " +
            "(:setor IS NULL OR LOWER(p.setorAtuacao) LIKE LOWER(CONCAT('%', :setor, '%'))) AND " +
            "(:categoriaId IS NULL OR cat.id = :categoriaId) AND " +
            "(:visivel IS NULL OR p.visivel = :visivel) AND " +
            "(:ativo IS NULL OR p.ativo = :ativo)")
    Page<Parceiro> buscarComFiltros(
            @Param("nome") String nome,
            @Param("tipo") TipoParceria tipo,
            @Param("setor") String setor,
            @Param("categoriaId") Long categoriaId,
            @Param("visivel") Boolean visivel,
            @Param("ativo") Boolean ativo,
            Pageable pageable);
}