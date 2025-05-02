package com.BancoC.server.auth.unitarios.services;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.SerializationUtils;

import com.BancoC.server.auth.GeneralTest;
import com.BancoC.server.auth.repositorios.PermisoRepositorio;
import com.BancoC.server.auth.repositorios.RolRepositorio;
import com.BancoC.server.auth.repositorios.RoleBindingRepositorio;
import com.BancoC.server.auth.repositorios.ScopeRepositorio;
import com.BancoC.server.auth.servicios.PermisoService;
import com.BancoC.server.auth.servicios.RolService;
import com.BancoC.server.auth.servicios.RoleBindingService;
import com.BancoC.server.auth.servicios.ScopeService;
import com.BancoC.server.auth.servicios.contratos.PermisoOps;
import com.BancoC.server.auth.servicios.contratos.RolOps;
import com.BancoC.server.auth.servicios.contratos.RoleBindingOps;
import com.BancoC.server.auth.servicios.contratos.ScopeOps;

public abstract class ServiceConfigTest extends GeneralTest {

    //Services
    protected PermisoOps permisoOps;
    protected RolOps rolOps;
    protected ScopeOps scopeOps;
    protected RoleBindingOps roleBindingOps;

    //Mock services
    protected PermisoOps mockPermisoOps;
    protected RolOps mockRolOps;
    protected ScopeOps mockScopeOps;
    protected UserDetailsService mockUserService;

    //Mock Repos
    private PermisoRepositorio permisoRepositorio;
    protected RolRepositorio rolRepositorio;
    protected ScopeRepositorio scopeRepositorio;
    protected RoleBindingRepositorio roleBindingRepositorio;

    @Override
    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();  //=> cargar objetos de pruebas

        //Mocks
        mockPermisoRepo();
        mockRol();
        mockScopeRepo();
        mockUsuario();
        mockRoleBinding();

        //Services
        definicionServices();
    }

    private void definicionServices() {
        permisoOps = new PermisoService(permisoRepositorio);
        rolOps = new RolService(rolRepositorio, mockPermisoOps);
        scopeOps = new ScopeService(scopeRepositorio);
        roleBindingOps = new RoleBindingService(mockUserService, roleBindingRepositorio, mockRolOps, mockScopeOps);
    }

    private void mockUsuario() {
        saraBD = SerializationUtils.clone(sara);
        saraBD.setUserId(1L);

        fernandoBD = SerializationUtils.clone(fernando);
        fernandoBD.setUserId(2L);
    }

    private void mockRoleBinding() throws NotFoundException {
        mockUserService = mock(UserDetailsService.class);
        mockRolOps = mock(RolOps.class);
        roleBindingRepositorio = mock(RoleBindingRepositorio.class);
        mockScopeOps = mock(ScopeOps.class);

        //RoleBindings
        saraAdminMiBancolombia.setScope(miBancolombiaBD);
        saraAdminMiBancolombia.setRol(adminBD);
        saraAdminMiBancolombia.setUsuario(sara);
        saraAdminMiBancolombiaBD = SerializationUtils.clone(saraAdminMiBancolombia);
        saraAdminMiBancolombiaBD.setRoleBindingId(10L);
        
        fernandoLectorNequiApp.setScope(nequiAppBD);
        fernandoLectorNequiApp.setRol(readerBD);
        fernandoLectorNequiApp.setUsuario(fernando);
        fernandoLectorNequiAppBD = SerializationUtils.clone(fernandoLectorNequiApp);
        fernandoLectorNequiAppBD.setRoleBindingId(11L);

        saraLectorNequiApp.setScope(nequiAppBD);
        saraLectorNequiApp.setRol(readerBD);
        saraLectorNequiApp.setUsuario(sara);
        saraLectorNequiAppBD = SerializationUtils.clone(saraLectorNequiApp);
        saraLectorNequiAppBD.setRoleBindingId(12L);

        //Mocks behavior
        when(roleBindingRepositorio.save(saraAdminMiBancolombia))
            .thenReturn(saraAdminMiBancolombiaBD);
        
        when(roleBindingRepositorio.findById(saraAdminMiBancolombiaBD.getRoleBindingId()))
            .thenReturn(Optional.of(saraAdminMiBancolombiaBD));

        when(mockUserService.loadUserByUsername(sara.getUsername()))
            .thenReturn(saraBD);

        when(mockRolOps.obtenerRol(adminBD.getRolId()))
            .thenReturn(adminBD);

        when(mockScopeOps.obtenerScope(miBancolombiaBD.getScopeId()))
            .thenReturn(miBancolombiaBD);

        when(mockUserService.loadUserByUsername(fernando.getUsername()))
            .thenThrow(new UsernameNotFoundException("Fernando not found"));

        when(roleBindingRepositorio.findByUsuarioAndScope(saraBD, miBancolombiaBD))
            .thenReturn(List.of(saraAdminMiBancolombiaBD));
        
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
