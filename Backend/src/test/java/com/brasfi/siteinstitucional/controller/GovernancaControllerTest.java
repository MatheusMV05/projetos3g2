package com.brasfi.siteinstitucional.controller;

import com.brasfi.siteinstitucional.dto.GovernancaDTO;
import com.brasfi.siteinstitucional.entity.Governanca;
import com.brasfi.siteinstitucional.service.GovernancaService;
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

@WebMvcTest(GovernancaController.class)
public class GovernancaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GovernancaService governancaService;

    @Autowired
    private ObjectMapper objectMapper;

    @WithMockUser(username = "admin", roles = {"USER"})
    @Test
    public void testListarTodasGovernancas() throws Exception {
        Governanca governanca = new Governanca();
        governanca.setId(1L);
        governanca.setSlug("slug-exemplo");

        when(governancaService.listarTodas()).thenReturn(List.of(governanca));

        mockMvc.perform(get("/api/governancas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].slug").value("slug-exemplo"));
    }

}
