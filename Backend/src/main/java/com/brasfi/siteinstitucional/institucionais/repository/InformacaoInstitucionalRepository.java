package com.brasfi.siteinstitucional.institucionais.repository;

import com.brasfi.siteinstitucional.institucionais.entity.InformacaoInstitucional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InformacaoInstitucionalRepository extends JpaRepository<InformacaoInstitucional, Long> {

    Optional<InformacaoInstitucional> findByChave(String chave);

    boolean existsByChave(String chave);

    List<InformacaoInstitucional> findByAtivo(boolean ativo);

    List<InformacaoInstitucional> findByTipo(String tipo);

    List<InformacaoInstitucional> findByAtivoAndTipo(boolean ativo, String tipo);
}