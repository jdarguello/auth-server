package com.BancoC.server.auth.unitarios.controladores;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.MediaType;

import com.BancoC.server.auth.modelos.Scope;
import com.fasterxml.jackson.core.JsonProcessingException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(ControladorConfigTest.ServiceConfig.class)
public class ScopeControladorTest extends ControladorConfigTest {
    
    @Test
    void obtenerScopeOK() throws Exception {
        MvcResult response = mockMvc.perform(
            get("/api/scope/{id}", miBancolombiaBD.getScopeId()))
            .andExpect(status().isOk())
        .andReturn();

        Scope scopeObtenido = objectMapper.readValue(
            response.getResponse().getContentAsString(),
            Scope.class
        );

        this.validarScope(miBancolombiaBD, scopeObtenido);
    }

    @Test
    void obtenerScopeNotFound() throws Exception {
        when(scopeService.obtenerScope(500))
            .thenThrow(new NotFoundException());

        mockMvc.perform(
            get("/api/scope/{id}", 500))
        .andExpect(status().isNotFound());
    }

    @Test
    void nuevoScope201() throws Exception {
        String requestBody = objectMapper.writeValueAsString(miBancolombia);

        MvcResult response = mockMvc.perform(
            post("/api/scope")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isCreated())
        .andReturn();

        Scope scopeCreado = objectMapper.readValue(
            response.getResponse().getContentAsString(),
            Scope.class
        );
        
        this.validarScope(miBancolombiaBD, scopeCreado);
    }

    @Test
    void borrarScope200() throws Exception {
        Integer id = miBancolombiaBD.getScopeId();
        mockMvc.perform(
            delete("/api/scope/{id}", id))
        .andExpect(status().isOk());
        
        verify(scopeService, times(1)).eliminarScope(id);
    }


    private void validarScope(
        Scope scopeRef, Scope scopeObtenido
    ) {
        assertEquals(scopeRef.getOwner(), scopeObtenido.getOwner());
        assertEquals(scopeRef.getAppName(), scopeObtenido.getAppName());
        assertEquals(scopeRef.getScopeId(), scopeObtenido.getScopeId());
    }
}
