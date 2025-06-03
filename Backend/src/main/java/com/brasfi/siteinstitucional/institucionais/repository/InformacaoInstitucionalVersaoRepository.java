package com.brasfi.siteinstitucional.institucionais.repository;

import com.brasfi.siteinstitucional.institucionais.entity.InformacaoInstitucionalVersao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InformacaoInstitucionalVersaoRepository extends JpaRepository<InformacaoInstitucionalVersao, Long> {

    Page<InformacaoInstitucionalVersao> findByInformacaoIdOrderByVersaoDesc(Long informacaoId, Pageable pageable);

    InformacaoInstitucionalVersao findTopByInformacaoIdOrderByVersaoDesc(Long informacaoId);
}