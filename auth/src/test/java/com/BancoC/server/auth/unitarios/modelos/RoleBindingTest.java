package com.BancoC.server.auth.unitarios.modelos;


import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.BancoC.server.auth.modelos.RoleBinding;

public class RoleBindingTest extends ModelosConfigTest {

    private RolTest rolTest;

    public RoleBindingTest() {
        this.rolTest = new RolTest();
    }
    
    @Test
    void crearRoleBinding() {
        validaciones(saraAdminMiBancolombiaBD, saraAdminMiBancolombia);
        validaciones(fernandoLectorNequiAppBD, fernandoLectorNequiApp);
    }

    @Test
    void obtenerRoleBindingsPorUsuario() {
        List<RoleBinding> roleBindings = roleBindingRepositorio.findByUsuario(fernando);

        assertEquals(1, roleBindings.size());
    }

    @Test
    void obtenerRol() {
        RoleBinding adminObtenido = roleBindingRepositorio.findById(
            saraAdminMiBancolombiaBD.getRoleBindingId()
        ).get();
        this.rolTest.validaciones(adminBD, adminObtenido.getRol());

        RoleBinding readerObtenido = roleBindingRepositorio.findById(
            fernandoLectorNequiAppBD.getRoleBindingId()
        ).get();

        this.rolTest.validaciones(readerBD, readerObtenido.getRol());
    }

    @Test
    void obtenerPermisos() {
        RoleBinding adminObtenido = roleBindingRepositorio.findById(
            saraAdminMiBancolombiaBD.getRoleBindingId()
        ).get();
        this.rolTest.validacionPermisos(adminObtenido.getRol(), List.of(
            readBD, writeBD, updateBD
        ));

        RoleBinding readerObtenido = roleBindingRepositorio.findById(
            fernandoLectorNequiAppBD.getRoleBindingId()
        ).get();

        this.rolTest.validacionPermisos(readerObtenido.getRol(), List.of(
            readBD
        ));
    }

    public void validaciones(RoleBinding referencia, RoleBinding roleBinding) {
        assertEquals(referencia.getRol(), roleBinding.getRol());
        assertEquals(referencia.getAuthority(), roleBinding.getRol().getNombre());
        assertEquals(referencia.getScope(), roleBinding.getScope());
        assertEquals(referencia.getUsuario(), roleBinding.getUsuario());
    }
}
