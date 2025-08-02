package com.BancoC.server.auth.unitarios.controladores;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.BancoC.server.auth.GeneralTest;
import com.BancoC.server.auth.servicios.contratos.PermisoOps;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@WebMvcTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test-unitarios")
public class ControladorConfigTest extends GeneralTest {
    
    @Autowired
    protected MockMvc mockMvc;

    protected ObjectMapper objectMapper;

    @TestConfiguration
    public static class ServiceConfig {

        @Bean
        public PermisoOps permisoService() {
            return mock(PermisoOps.class);
        }

    }

    @Autowired
    private PermisoOps permisoService;

    @Override
    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();  //Recupera objetos de pruebas

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        //Comportamiento de mocks
        this.permisosMocks();

    }

    private void permisosMocks() throws NotFoundException {
        when(permisoService.obtenerPermisos())
            .thenReturn(List.of(readBD, writeBD, updateBD));
        
        when(permisoService.obtenerPorId(readBD.getPermisoId()))
            .thenReturn(readBD);

        when(permisoService.obtenerPorId(125))
            .thenThrow(new NotFoundException());
        
        when(permisoService.obtenerPorNombre("noExiste"))
            .thenThrow(new NotFoundException());
        
        when(permisoService.obtenerPorNombre(write.getNombre()))
            .thenReturn(writeBD);
    }
    
}
