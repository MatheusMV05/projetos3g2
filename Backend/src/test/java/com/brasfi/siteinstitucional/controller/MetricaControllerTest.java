package com.brasfi.siteinstitucional.controller;

import com.brasfi.siteinstitucional.dto.MetricaDTO;
import com.brasfi.siteinstitucional.entity.Metrica;
import com.brasfi.siteinstitucional.service.MetricaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MetricaController.class)
public class MetricaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MetricaService metricaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void listarMetricas_deveRetornarPagina() throws Exception {
        Metrica metrica = new Metrica();
        metrica.setId(1L);
        metrica.setNome("Métrica Teste");
        metrica.setDescricao("Descrição teste");
        metrica.setValor(new BigDecimal("10.5"));
        metrica.setUnidade("un");
        metrica.setDataReferencia(LocalDate.now());

        Page<Metrica> page = new PageImpl<>(Collections.singletonList(metrica));
        Mockito.when(metricaService.listarTodas(anyString(), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/metricas")
                        .param("nome", "Teste")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(metrica.getId()))
                .andExpect(jsonPath("$.content[0].nome").value(metrica.getNome()));
    }

    @Test
    public void buscarPorId_deveRetornarMetrica() throws Exception {
        Metrica metrica = new Metrica();
        metrica.setId(1L);
        metrica.setNome("Métrica Teste");

        Mockito.when(metricaService.buscarPorId(1L)).thenReturn(metrica);

        mockMvc.perform(get("/api/metricas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Métrica Teste"));
    }

    @Test
    public void criar_deveRetornarCriado() throws Exception {
        MetricaDTO dto = new MetricaDTO();
        dto.setNome("Nova Métrica");
        dto.setDescricao("Descrição");
        dto.setValor(new BigDecimal("20"));
        dto.setUnidade("kg");
        dto.setDataReferencia(LocalDate.now());

        Metrica salvo = new Metrica();
        salvo.setId(1L);
        salvo.setNome(dto.getNome());

        Mockito.when(metricaService.salvar(any(MetricaDTO.class))).thenReturn(salvo);

        mockMvc.perform(post("/api/metricas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Nova Métrica"));
    }

    @Test
    public void atualizar_deveRetornarOk() throws Exception {
        MetricaDTO dto = new MetricaDTO();
        dto.setNome("Atualizada");
        dto.setDescricao("Descrição");
        dto.setValor(new BigDecimal("30"));
        dto.setUnidade("m");
        dto.setDataReferencia(LocalDate.now());

        Metrica atualizado = new Metrica();
        atualizado.setId(1L);
        atualizado.setNome(dto.getNome());

        Mockito.when(metricaService.atualizar(eq(1L), any(MetricaDTO.class))).thenReturn(atualizado);

        mockMvc.perform(put("/api/metricas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Atualizada"));
    }

    @Test
    public void excluir_deveRetornarNoContent() throws Exception {
        Mockito.doNothing().when(metricaService).excluir(1L);

        mockMvc.perform(delete("/api/metricas/1"))
                .andExpect(status().isNoContent());
    }
}
