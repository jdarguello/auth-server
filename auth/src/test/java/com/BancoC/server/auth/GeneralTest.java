package com.BancoC.server.auth;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import com.BancoC.server.auth.modelos.Permiso;
import com.BancoC.server.auth.modelos.Rol;
import com.BancoC.server.auth.modelos.RoleBinding;
import com.BancoC.server.auth.modelos.Scope;
import com.BancoC.server.auth.modelos.Usuario;

public class GeneralTest {
    
    //Usuarios
    protected Usuario sara;
    protected Usuario fernando;

    protected Usuario saraBD;
    protected Usuario fernandoBD;

    //RoleBindings
    protected RoleBinding saraAdminMiBancolombia;
    protected RoleBinding saraLectorNequiApp;
    protected RoleBinding fernandoLectorNequiApp;
    protected RoleBinding fernandoWriterMiBancolombia;

    protected RoleBinding saraAdminMiBancolombiaBD;
    protected RoleBinding saraLectorNequiAppBD;
    protected RoleBinding fernandoLectorNequiAppBD;
    protected RoleBinding fernandoWriterMiBancolombiaBD;

    //Roles
    protected Rol admin;
    protected Rol reader;
    protected Rol writerAndReader;

    protected Rol adminBD;
    protected Rol readerBD;
    protected Rol writerAndReaderBD;

    //Scopes
    protected Scope miBancolombia;
    protected Scope nequiApp;

    protected Scope miBancolombiaBD;
    protected Scope nequiAppBD;


    //Permisos
    protected Permiso read;
    protected Permiso write;
    protected Permiso update;

    protected Permiso readBD;
    protected Permiso writeBD;
    protected Permiso updateBD;


    @BeforeEach
    public void setUp() throws Exception {
        definicionPermisos();
        definicionScopes();
        definicionesRoles();
        definicionRoleBindings();
        definicionUsuarios();
    }

    private void definicionUsuarios() {
        sara = Usuario.builder()
            .username("sara@gmail.com")
            .password("super-secret123")
        .build();
    
        fernando = Usuario.builder()
            .username("admin@gmail.com")
            .password("secret123")
        .build();
    }

    private void definicionRoleBindings() {
        saraAdminMiBancolombia = RoleBinding.builder()
            .rol(admin)
            .scope(miBancolombia)
            .usuario(sara)
        .build();

        fernandoLectorNequiApp = RoleBinding.builder()
            .rol(reader)
            .scope(nequiApp)
            .usuario(fernando)
        .build();
    }

    private void definicionPermisos() {
        read = Permiso.builder()
            .nombre("leer")
            .descripcion("lectura de datos")
        .build();

        write = Permiso.builder()
            .nombre("escribir")
            .descripcion("escritura de datos")
        .build();

        update = Permiso.builder()
            .nombre("actualizar")
            .descripcion("actualizar info")
        .build();
    }

    private void definicionScopes() {
        miBancolombia = Scope.builder()
            .appName("Mi Bancolombia")
            .owner("Bancolombia")
        .build();

        nequiApp = Scope.builder()
            .appName("Nequi App")
            .owner("Nequi")
        .build();
    }

    private void definicionesRoles() {
        admin = Rol.builder()
            .nombre("admin")
        .build();

        reader = Rol.builder()
            .nombre("lector")
        .build();

        writerAndReader = Rol.builder()
            .nombre("escritor")
        .build();
    }

}
