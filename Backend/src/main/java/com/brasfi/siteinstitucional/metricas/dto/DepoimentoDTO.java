package com.brasfi.siteinstitucional.metricas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepoimentoDTO {

    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    private String nome;

    @NotBlank(message = "Cargo é obrigatório")
    @Size(max = 100, message = "Cargo deve ter no máximo 100 caracteres")
    private String cargo;

    @NotBlank(message = "Organização é obrigatória")
    @Size(max = 200, message = "Organização deve ter no máximo 200 caracteres")
    private String organizacao;

    @NotBlank(message = "Texto é obrigatório")
    @Size(min = 50, max = 1000, message = "Texto deve ter entre 50 e 1000 caracteres")
    private String texto;

    private String fotoUrl;

    private String iniciativaRelacionada;

    @NotNull(message = "Ano é obrigatório")
    private Integer ano;

    private Integer ordem = 0;

    private boolean destaque = false;

    private boolean ativo = true;
}