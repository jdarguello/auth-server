package com.BancoC.server.auth.unitarios.modelos;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.BancoC.server.auth.GeneralTest;
import com.BancoC.server.auth.repositorios.PermisoRepositorio;
import com.BancoC.server.auth.repositorios.RolRepositorio;
import com.BancoC.server.auth.repositorios.RoleBindingRepositorio;
import com.BancoC.server.auth.repositorios.ScopeRepositorio;
import com.BancoC.server.auth.repositorios.UsuarioRepositorio;

@DataJpaTest
@ActiveProfiles("test-unitarios")
public abstract class ModelosConfigTest extends GeneralTest {
    
    @Autowired
    protected UsuarioRepositorio ususarioRepositorio;
    @Autowired
    protected PermisoRepositorio permisoRepositorio;
    @Autowired
    protected ScopeRepositorio scopeRepositorio;
    @Autowired
    protected RolRepositorio rolRepositorio;
    @Autowired
    protected RoleBindingRepositorio roleBindingRepositorio;

    @Override
    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();  //=> cargar objetos de pruebas

        permisosBD();
        scopeBD();
        rolesBD();
        usuariosBD();
        roleBindingBD();
    }

    private void roleBindingBD() {
        //Vinculación de rol
        saraAdminMiBancolombia.setRol(adminBD);
        saraAdminMiBancolombia.setScope(miBancolombiaBD);
        saraAdminMiBancolombia.setUsuario(saraBD);

        fernandoLectorNequiApp.setRol(readerBD);
        fernandoLectorNequiApp.setScope(nequiAppBD);
        fernandoLectorNequiApp.setUsuario(fernandoBD);

        //Almacenamiento del rol-binding
        saraAdminMiBancolombiaBD = roleBindingRepositorio.save(saraAdminMiBancolombia);
        fernandoLectorNequiAppBD = roleBindingRepositorio.save(fernandoLectorNequiApp);
    }

    private void usuariosBD() {
        saraBD = ususarioRepositorio.save(sara);
        fernandoBD = ususarioRepositorio.save(fernando);
    }

    private void permisosBD() {
        writeBD = permisoRepositorio.save(write);
        readBD = permisoRepositorio.save(read);
        updateBD = permisoRepositorio.save(update);
    }

    private void scopeBD() {
        miBancolombiaBD = scopeRepositorio.save(miBancolombia);
        nequiAppBD = scopeRepositorio.save(nequiApp);
    }

    private void rolesBD() {
        //Vinculación de permisos
        reader.getPermisos().add(readBD);
        admin.getPermisos().add(readBD);
        admin.getPermisos().add(writeBD);
        admin.getPermisos().add(updateBD);

        //Almacenamiento de roles
        readerBD = rolRepositorio.save(reader);
        writerAndReaderBD = rolRepositorio.save(writerAndReader);
        adminBD = rolRepositorio.save(admin);
        

    }

}
