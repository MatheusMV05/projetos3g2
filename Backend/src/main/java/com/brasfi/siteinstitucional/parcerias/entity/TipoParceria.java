package com.brasfi.siteinstitucional.parcerias.entity;

public enum TipoParceria {
    MEMBRO_ASSOCIADO("Membro Associado"),
    PARCEIRO_ESTRATEGICO("Parceiro Estratégico"),
    APOIADOR("Apoiador"),
    PATROCINADOR("Patrocinador"),
    OUTRO("Outro");

    private final String descricao;

    TipoParceria(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}