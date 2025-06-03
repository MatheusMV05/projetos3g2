package com.brasfi.dto;

import lombok.Data;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

@Data
public class TagDTO {
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 255, message = "Nome deve ter no máximo 255 caracteres")
    private String name;

    @Size(max = 255, message = "Slug deve ter no máximo 255 caracteres")
    private String slug;

    private Integer usageCount;
    private LocalDateTime createdAt;
}
