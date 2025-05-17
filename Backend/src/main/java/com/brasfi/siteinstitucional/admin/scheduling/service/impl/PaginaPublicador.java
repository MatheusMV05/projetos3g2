package com.brasfi.siteinstitucional.admin.scheduling.service.impl;

import com.brasfi.siteinstitucional.admin.scheduling.entity.PublicacaoAgendada;
import com.brasfi.siteinstitucional.admin.scheduling.service.PublicadorConteudo;
import com.brasfi.siteinstitucional.dto.PaginaDTO;
import com.brasfi.siteinstitucional.entity.Pagina;
import com.brasfi.siteinstitucional.mediator.PaginaMediator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaginaPublicador implements PublicadorConteudo {

    private final PaginaMediator paginaMediator;
    private final ObjectMapper objectMapper;

    @Override
    public boolean suportaTipo(String tipoConteudo) {
        return "PAGINA".equals(tipoConteudo);
    }

    @Override
    public void publicar(PublicacaoAgendada publicacao) {
        try {
            // Converter o JSON para o DTO
            PaginaDTO paginaDTO = objectMapper.readValue(publicacao.getConteudoJson(), PaginaDTO.class);

            if (publicacao.getConteudoId() != null) {
                // Atualizar página existente
                Pagina pagina = paginaMediator.atualizar(publicacao.getConteudoId(), paginaDTO);
                log.info("Página {} atualizada com sucesso", pagina.getId());
            } else {
                // Criar nova página
                Pagina pagina = paginaMediator.salvar(paginaDTO);
                log.info("Página {} criada com sucesso", pagina.getId());
            }
        } catch (Exception e) {
            log.error("Erro ao publicar página: {}", e.getMessage(), e);
            throw new RuntimeException("Erro ao publicar página", e);
        }
    }
}