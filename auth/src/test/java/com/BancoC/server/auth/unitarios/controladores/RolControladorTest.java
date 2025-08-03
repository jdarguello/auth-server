package com.BancoC.server.auth.unitarios.controladores;

import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import com.BancoC.server.auth.modelos.Permiso;
import com.BancoC.server.auth.modelos.Rol;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(ControladorConfigTest.ServiceConfig.class)
public class RolControladorTest extends ControladorConfigTest {
    
    @Test
    void obtenerRoles() throws Exception {
        MvcResult response = mockMvc.perform(
            get("/api/rol/all"))
            .andExpect(status().isOk())
        .andReturn();

        Collection<Rol> permisosObtenidos = objectMapper.readValue(
            response.getResponse().getContentAsString(),
            new TypeReference<Collection<Rol>>() {}
        );

        List.of(readerBD, adminBD, writerAndReaderBD).stream().forEach(
            rolRef -> {
                assertTrue(permisosObtenidos.contains(rolRef));
            }
        );
    }

    @Test
    void obtenerRolPorIdOK() throws Exception {
        MvcResult response = mockMvc.perform(
            get("/api/rol/{id}", readerBD.getRolId()))
            .andExpect(status().isOk())
        .andReturn();

        Rol rolObtenido = objectMapper.readValue(
            response.getResponse().getContentAsString(),
            Rol.class
        );

        this.validarRol(readerBD, rolObtenido);
    }

    @Test
    void obtenerRolPorIdNotFound() throws Exception {
        mockMvc.perform(
            get("/api/rol/{id}", 244))
        .andExpect(status().isNotFound());
    }

    @Test
    void nuevoRol201() throws Exception {
        String requestBody = objectMapper.writeValueAsString(admin);

        MvcResult response = mockMvc.perform(
            post("/api/rol/nuevo")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isCreated())
        .andReturn();

        Rol rolCreado = objectMapper.readValue(
            response.getResponse().getContentAsString(),
            Rol.class
        );

        this.validarRol(adminBD, rolCreado);
    }

    @Test
    void nuevoRol400() throws Exception {
        String requestBody = objectMapper.writeValueAsString(
            Rol.builder()
                .nombre("")
            .build()
        );

        mockMvc.perform(
            post("/api/rol/nuevo")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().isBadRequest());
    }

    @Test
    void addPermisos200 () throws Exception {
        String requestBody = objectMapper.writeValueAsString(
            List.of(writeBD, readBD)
        );

        mockMvc.perform(
            put("/api/rol/{id}", writerAndReaderBD.getRolId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().isOk());
    }

    

    private void validarRol(
        Rol rolRef, Rol rolObtenido
    ) {
        assertEquals(rolRef.getRolId(), rolObtenido.getRolId());
        assertEquals(rolRef.getNombre(), rolObtenido.getNombre());
        assertEquals(rolRef.getPermisos(), rolObtenido.getPermisos());
    }

}
