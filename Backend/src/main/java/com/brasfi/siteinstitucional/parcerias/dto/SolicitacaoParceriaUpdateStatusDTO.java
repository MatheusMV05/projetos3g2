package com.brasfi.siteinstitucional.parcerias.dto;

import com.brasfi.siteinstitucional.parcerias.entity.StatusSolicitacao;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SolicitacaoParceriaUpdateStatusDTO {
    @NotNull(message = "Status é obrigatório")
    private StatusSolicitacao status;
    private String observacoesAdmin;
}