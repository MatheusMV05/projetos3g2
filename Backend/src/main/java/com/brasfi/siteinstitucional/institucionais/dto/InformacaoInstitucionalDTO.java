package com.brasfi.siteinstitucional.institucionais.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InformacaoInstitucionalDTO {

    private Long id;

    @NotBlank(message = "Chave é obrigatória")
    @Pattern(regexp = "^[a-zA-Z0-9_.-]+$", message = "Chave deve conter apenas letras, números, underscores, pontos e hífens")
    private String chave;

    @NotBlank(message = "Valor é obrigatório")
    private String valor;

    @NotBlank(message = "Tipo é obrigatório")
    private String tipo;

    private String descricao;

    private boolean ativo = true;
}