package com.brasfi.siteinstitucional.controller;

import com.brasfi.siteinstitucional.dto.SobreDTO;
import com.brasfi.siteinstitucional.entity.Sobre;
import com.brasfi.siteinstitucional.service.SobreService;
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

@WebMvcTest(SobreController.class)
public class SobreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SobreService sobreService;

    @Autowired
    private ObjectMapper objectMapper;

    @WithMockUser(username = "admin", roles = {"USER"})
    @Test
    public void testListarTodosSobre() throws Exception {
        Sobre sobre = new Sobre();
        sobre.setId(1L);
        sobre.setSlug("sobre-slug-exemplo");

        when(sobreService.listarTodos()).thenReturn(List.of(sobre));

        mockMvc.perform(get("/api/sobre"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].slug").value("sobre-slug-exemplo"));
    }
}