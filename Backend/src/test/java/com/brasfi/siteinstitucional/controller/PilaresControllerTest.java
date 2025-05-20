package com.brasfi.siteinstitucional.controller;

import com.brasfi.siteinstitucional.dto.PilaresDTO;
import com.brasfi.siteinstitucional.entity.Pilares;
import com.brasfi.siteinstitucional.service.PilaresService;
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

@WebMvcTest(PilaresController.class)
public class PilaresControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PilaresService pilaresService;

    @Autowired
    private ObjectMapper objectMapper;

    @WithMockUser(username = "admin", roles = {"USER"})
    @Test
    public void testListarTodosPilares() throws Exception {
        Pilares pilares = new Pilares();
        pilares.setId(1L);
        pilares.setSlug("pilares-slug-exemplo");

        when(pilaresService.listarTodos()).thenReturn(List.of(pilares));

        mockMvc.perform(get("/api/pilares"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].slug").value("pilares-slug-exemplo"));
    }
}