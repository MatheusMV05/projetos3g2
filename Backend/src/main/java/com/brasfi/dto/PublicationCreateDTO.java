package com.brasfi.dto;

import com.brasfi.model.PublicationType;
import com.brasfi.model.PublicationStatus;
import lombok.Data;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class PublicationCreateDTO {

    @NotBlank(message = "Título é obrigatório")
    @Size(max = 500, message = "Título deve ter no máximo 500 caracteres")
    private String title;

    @Size(max = 500, message = "Slug deve ter no máximo 500 caracteres")
    private String slug;

    @Size(max = 2000, message = "Resumo deve ter no máximo 2000 caracteres")
    private String summary;

    private String content;

    @NotNull(message = "Tipo é obrigatório")
    private PublicationType type;

    private PublicationStatus status = PublicationStatus.DRAFT;

    private Long categoryId;

    private LocalDateTime scheduledAt;

    private Boolean featured = false;

    @Size(max = 255, message = "Meta título deve ter no máximo 255 caracteres")
    private String metaTitle;

    @Size(max = 500, message = "Meta descrição deve ter no máximo 500 caracteres")
    private String metaDescription;

    private String searchKeywords;

    private Set<Long> tagIds;
}