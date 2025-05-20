package com.brasfi.siteinstitucional.controller;

import com.brasfi.siteinstitucional.dto.HistoriaDTO;
import com.brasfi.siteinstitucional.entity.Historia;
import com.brasfi.siteinstitucional.service.HistoriaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HistoriaController.class)
public class HistoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HistoriaService historiaService;

    @Autowired
    private ObjectMapper objectMapper;

    @WithMockUser(username = "admin", roles = {"USER"})
    @Test
    public void testListarTodasHistorias() throws Exception {
        Historia historia = new Historia();
        historia.setId(1L);
        historia.setSlug("historia-slug-exemplo");

        when(historiaService.listarTodas()).thenReturn(List.of(historia));

        mockMvc.perform(get("/api/historias"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].slug").value("historia-slug-exemplo"));
    }
}