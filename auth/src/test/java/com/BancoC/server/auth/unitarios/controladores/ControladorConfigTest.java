package com.BancoC.server.auth.unitarios.controladores;

import static org.mockito.Mockito.doThrow;
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
import com.BancoC.server.auth.modelos.Rol;
import com.BancoC.server.auth.servicios.contratos.PermisoOps;
import com.BancoC.server.auth.servicios.contratos.RolOps;
import com.BancoC.server.auth.servicios.contratos.ScopeOps;
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

        @Bean
        public RolOps rolService() {
            return mock(RolOps.class);
        }

        @Bean
        public ScopeOps scopeService() {
            return mock(ScopeOps.class);
        }

    }

    @Autowired
    protected PermisoOps permisoService;

    @Autowired
    protected RolOps rolService;

    @Autowired
    protected ScopeOps scopeService;

    @Override
    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();  //Recupera objetos de pruebas

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        //Comportamiento de mocks
        this.permisosMocks();
        this.rolMocks();
        this.scopeMocks();
    }

    private void scopeMocks() throws NotFoundException {
        when(scopeService.obtenerScope(miBancolombiaBD.getScopeId()))
            .thenReturn(miBancolombiaBD);

        when(scopeService.nuevoScope(miBancolombia))
            .thenReturn(miBancolombiaBD);
    }

    private void rolMocks() throws NotFoundException {
        when(rolService.obtenerRoles())
            .thenReturn(List.of(adminBD, readerBD, writerAndReaderBD));
        
        when(rolService.obtenerRol(readerBD.getRolId()))
            .thenReturn(readerBD);

        when(rolService.nuevoRol(admin))
            .thenReturn(adminBD);
    }

    private void permisosMocks() throws NotFoundException {
        when(permisoService.obtenerPermisos())
            .thenReturn(List.of(readBD, writeBD, updateBD));
        
        when(permisoService.obtenerPorId(readBD.getPermisoId()))
            .thenReturn(readBD);
        
        when(permisoService.obtenerPorNombre(write.getNombre()))
            .thenReturn(writeBD);
    }
    
}
