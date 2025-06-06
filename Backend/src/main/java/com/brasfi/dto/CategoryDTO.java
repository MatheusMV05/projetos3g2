package com.brasfi.dto;

import lombok.Data;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

@Data
public class CategoryDTO {
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 255, message = "Nome deve ter no máximo 255 caracteres")
    private String name;

    @Size(max = 255, message = "Slug deve ter no máximo 255 caracteres")
    private String slug;

    @Size(max = 1000, message = "Descrição deve ter no máximo 1000 caracteres")
    private String description;

    private boolean active = true;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Long publicationCount;
}