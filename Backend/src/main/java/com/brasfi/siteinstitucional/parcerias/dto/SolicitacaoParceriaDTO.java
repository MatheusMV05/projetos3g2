package com.brasfi.siteinstitucional.parcerias.dto;

import com.brasfi.siteinstitucional.parcerias.entity.StatusSolicitacao;
import com.brasfi.siteinstitucional.parcerias.entity.TipoParceria;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SolicitacaoParceriaDTO {
    private Long id;
    private String nomeOrganizacao;
    private String nomeContato;
    private String emailContato;
    private String telefoneContato;
    private TipoParceria tipoSolicitacao;
    private String mensagemProposta;
    private StatusSolicitacao status;
    private LocalDateTime dataSolicitacao;
    private LocalDateTime dataUltimaAtualizacaoStatus;
    private Long adminResponsavelId;
    private String adminResponsavelNome;
    private String observacoesAdmin;
}