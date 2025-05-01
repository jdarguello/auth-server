package com.BancoC.server.auth.unitarios.services;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.util.SerializationUtils;

import com.BancoC.server.auth.GeneralTest;
import com.BancoC.server.auth.repositorios.PermisoRepositorio;
import com.BancoC.server.auth.repositorios.RolRepositorio;
import com.BancoC.server.auth.repositorios.ScopeRepositorio;
import com.BancoC.server.auth.servicios.PermisoService;
import com.BancoC.server.auth.servicios.RolService;
import com.BancoC.server.auth.servicios.ScopeService;
import com.BancoC.server.auth.servicios.contratos.PermisoOps;
import com.BancoC.server.auth.servicios.contratos.RolOps;
import com.BancoC.server.auth.servicios.contratos.ScopeOps;

public abstract class ServiceConfigTest extends GeneralTest {

    //Services
    protected PermisoOps permisoOps;
    protected RolOps rolOps;
    protected ScopeOps scopeOps;

    //Mock services
    protected PermisoOps mockPermisoOps;

    //Mock Repos
    private PermisoRepositorio permisoRepositorio;
    protected RolRepositorio rolRepositorio;
    protected ScopeRepositorio scopeRepositorio;

    @Override
    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();  //=> cargar objetos de pruebas

        //Mocks
        mockPermisoRepo();
        mockRol();
        mockScopeRepo();

        //Services
        definicionServices();
    }

    private void definicionServices() {
        permisoOps = new PermisoService(permisoRepositorio);
        rolOps = new RolService(rolRepositorio, mockPermisoOps);
        scopeOps = new ScopeService(scopeRepositorio);
    }

    private void mockScopeRepo() {
        scopeRepositorio = mock(ScopeRepositorio.class);

        //Scopes
        miBancolombiaBD = SerializationUtils.clone(miBancolombia);
        miBancolombiaBD.setScopeId(1);

        nequiAppBD = SerializationUtils.clone(nequiApp);
        nequiAppBD.setScopeId(2);

        //Mocks behavior
        when(scopeRepositorio.save(miBancolombia))
            .thenReturn(miBancolombiaBD);
        
        when(scopeRepositorio.findById(miBancolombiaBD.getScopeId()))
            .thenReturn(Optional.of(miBancolombiaBD));

        when(scopeRepositorio.findAll())
            .thenReturn(List.of(miBancolombiaBD, nequiAppBD));

        when(scopeRepositorio.findByOwner(miBancolombia.getOwner()))
            .thenReturn(List.of(miBancolombiaBD));

        when(scopeRepositorio.findByOwner(nequiApp.getOwner()))
            .thenReturn(List.of(nequiAppBD));
    }

    private void mockRol() throws NotFoundException {
        rolRepositorio = spy(RolRepositorio.class);
        mockPermisoOps = mock(PermisoOps.class);

        //Roles
        readerBD = SerializationUtils.clone(reader);
        readerBD.setRolId(1L);

        adminBD = SerializationUtils.clone(admin);
        adminBD.setRolId(5L);

        //Mocks Repo behavior
        when(rolRepositorio.save(admin))
            .thenReturn(adminBD);

        when(rolRepositorio.findById(adminBD.getRolId()))
            .thenReturn(Optional.of(adminBD));

        when(rolRepositorio.findById(readerBD.getRolId()))
            .thenReturn(Optional.of(readerBD));
        
        when(rolRepositorio.findAll())
            .thenReturn(List.of(adminBD, readerBD));

        //Mocks service behavior
        when(mockPermisoOps.obtenerPorId(readBD.getPermisoId()))
            .thenReturn(readBD);

        when(mockPermisoOps.obtenerPorId(100))
            .thenThrow(new NotFoundException());

    }

    private void mockPermisoRepo() {
        permisoRepositorio = mock(PermisoRepositorio.class);

        //Permisos
        readBD = SerializationUtils.clone(read);
        readBD.setPermisoId(10);

        writeBD = SerializationUtils.clone(write);
        writeBD.setPermisoId(11);

        //Mocks behaviors
        when(permisoRepositorio.findById(10))
            .thenReturn(Optional.of(readBD));

        when(permisoRepositorio.findByNombre(writeBD.getNombre()))
            .thenReturn(Optional.of(writeBD));

        when(permisoRepositorio.findAll())
            .thenReturn(List.of(readBD, writeBD));
    }

}
