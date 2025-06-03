package com.brasfi.model;

public enum PublicationType {
    ARTICLE("Artigo"),
    RESEARCH("Pesquisa"),
    NEWS("Notícia");

    private final String displayName;

    PublicationType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}