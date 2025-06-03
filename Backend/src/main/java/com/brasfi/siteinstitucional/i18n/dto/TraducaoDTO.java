package com.brasfi.siteinstitucional.i18n.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TraducaoDTO {
    private Long id;

    @NotBlank(message = "Chave da tradução é obrigatória")
    @Size(max = 255)
    private String chave;

    @NotBlank(message = "Idioma é obrigatório")
    @Size(min = 2, max = 10, message = "Idioma deve ter entre 2 e 10 caracteres (ex: pt-BR, en)")
    private String idioma;

    @NotBlank(message = "Valor da tradução é obrigatório")
    private String valor;
}