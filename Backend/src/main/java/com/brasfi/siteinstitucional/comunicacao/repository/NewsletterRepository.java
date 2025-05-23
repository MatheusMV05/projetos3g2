package com.brasfi.siteinstitucional.comunicacao.repository;

import com.brasfi.siteinstitucional.comunicacao.entity.Newsletter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsletterRepository extends JpaRepository<Newsletter, Long> {

    Page<Newsletter> findByEnviada(boolean enviada, Pageable pageable);

    Page<Newsletter> findByUsuarioId(Long usuarioId, Pageable pageable);
}