package com.brasfi.model;

public enum PublicationStatus {
    DRAFT("Rascunho"),
    PUBLISHED("Publicado"),
    ARCHIVED("Arquivado"),
    SCHEDULED("Agendado");

    private final String displayName;

    PublicationStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}