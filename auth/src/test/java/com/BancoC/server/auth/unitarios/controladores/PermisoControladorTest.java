package com.BancoC.server.auth.unitarios.controladores;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MvcResult;

import com.BancoC.server.auth.modelos.Permiso;
import com.fasterxml.jackson.core.type.TypeReference;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collection;
import java.util.List;

@Import(ControladorConfigTest.ServiceConfig.class)
public class PermisoControladorTest extends ControladorConfigTest {
    
    @Test
    void obtenerPermisos() throws Exception {
        MvcResult response = mockMvc.perform(
            get("/api/permiso/all"))
            .andExpect(status().isOk())
        .andReturn();

        Collection<Permiso> permisosObtenidos = objectMapper.readValue(
            response.getResponse().getContentAsString(),
            new TypeReference<Collection<Permiso>>() {}
        );

        List.of(readBD, writeBD, updateBD).stream().forEach(
            permisoRef -> {
                assertTrue(permisosObtenidos.contains(permisoRef));
            }
        );
    }

    @Test
    void obtenerPermisoPorIdOK() throws Exception  {
        MvcResult response = mockMvc.perform(
            get("/api/permiso/{id}", readBD.getPermisoId()))
            .andExpect(status().isOk())
        .andReturn();

        Permiso permisoObtenido = objectMapper.readValue(
            response.getResponse().getContentAsString(),
            Permiso.class
        );

        this.validarPermiso(readBD, permisoObtenido);
    }

    @Test
    void obtenerPermisoPorIdNotFound() throws Exception {
        mockMvc.perform(
            get("/api/permiso/{id}", 125))
        .andExpect(status().isNotFound());
    }

    @Test
    void obtenerPermisoPorNombreOK() throws Exception {
        MvcResult response = mockMvc.perform(
            get("/api/permiso")
            .param("nombre", write.getNombre()))
            .andExpect(status().isOk())
        .andReturn();

        Permiso permisoObtenido = objectMapper.readValue(
            response.getResponse().getContentAsString(),
            Permiso.class
        );

        this.validarPermiso(writeBD, permisoObtenido);
    }

    @Test
    void obtenerPermisoPorNombreNotFound() throws Exception {
        mockMvc.perform(
            get("/api/permiso")
            .param("nombre", "noExiste"))
        .andExpect(status().isNotFound());
    }

    private void validarPermiso(
        Permiso permisoRef, Permiso permisoObtenido
    ) {
        assertEquals(permisoRef.getPermisoId(), permisoObtenido.getPermisoId());
        assertEquals(permisoRef.getNombre(), permisoObtenido.getNombre());
        assertEquals(permisoRef.getDescripcion(), permisoObtenido.getDescripcion());
    }

}
