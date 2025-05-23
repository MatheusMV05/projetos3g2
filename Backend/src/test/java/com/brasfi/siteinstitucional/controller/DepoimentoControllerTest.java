package com.brasfi.siteinstitucional.controller;

import com.brasfi.siteinstitucional.entity.Depoimento;
import com.brasfi.siteinstitucional.service.DepoimentoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DepoimentoController.class)
public class DepoimentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepoimentoService depoimentoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void listarDepoimentos_deveRetornarPagina() throws Exception {
        Depoimento depoimento = new Depoimento();
        depoimento.setId(1L);
        //depoimento.setNome("Fulano");

        Page<Depoimento> page = new PageImpl<>(Collections.singletonList(depoimento));
        Mockito.when(depoimentoService.listarTodos(anyString(), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/depoimentos")
                        .param("nome", "Fulano")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(depoimento.getId()));
                //.andExpect(jsonPath("$.content[0].nome").value(depoimento.getNome()));
    }

    @Test
    public void buscarPorId_deveRetornarDepoimento() throws Exception {
        Depoimento depoimento = new Depoimento();
        depoimento.setId(1L);
        //depoimento.setNome("Fulano");

        Mockito.when(depoimentoService.buscarPorId(1L)).thenReturn(depoimento);

        mockMvc.perform(get("/api/depoimentos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Fulano"));
    }
}
