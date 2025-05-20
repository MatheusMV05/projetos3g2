package com.brasfi.siteinstitucional.comunicacao.dto;

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
public class RespostaContatoDTO {

    @NotBlank(message = "Resposta é obrigatória")
    @Size(min = 10, message = "Resposta deve ter no mínimo 10 caracteres")
    private String resposta;
}