package com.brasfi.siteinstitucional.admin.scheduling.service;

import com.brasfi.siteinstitucional.admin.scheduling.entity.PublicacaoAgendada;

public interface PublicadorConteudo {

    boolean suportaTipo(String tipoConteudo);

    void publicar(PublicacaoAgendada publicacao);
}