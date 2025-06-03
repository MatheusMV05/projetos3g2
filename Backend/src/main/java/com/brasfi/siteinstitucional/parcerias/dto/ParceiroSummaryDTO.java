package com.brasfi.siteinstitucional.parcerias.dto;

import com.brasfi.siteinstitucional.parcerias.entity.TipoParceria;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParceiroSummaryDTO {
    private Long id;
    private String nomeOrganizacao;
    private String descricaoCurta; // Um resumo da descrição
    private String logoUrl;
    private String siteUrl;
    private TipoParceria tipoParceria;
    private String setorAtuacao;
    private List<String> nomesCategorias;
}