package com.brasfi.siteinstitucional.admin.scheduling.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublicacaoAgendadaDTO {

    private Long id;

    @NotBlank(message = "O tipo de conteúdo é obrigatório")
    private String tipoConteudo;

    private Long conteudoId;

    private String conteudoJson;

    @NotNull(message = "A data de publicação é obrigatória")
    @Future(message = "A data de publicação deve ser futura")
    private LocalDateTime dataPublicacao;
}