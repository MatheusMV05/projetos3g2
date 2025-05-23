package com.brasfi.siteinstitucional.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GovernancaDTO {
    private Long id;

    @NotBlank(message = "O slug é obrigatório")
    private String slug;
}
