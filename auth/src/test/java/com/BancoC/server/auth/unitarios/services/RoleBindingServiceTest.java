package com.BancoC.server.auth.unitarios.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.BancoC.server.auth.modelos.RoleBinding;

public class RoleBindingServiceTest extends ServiceConfigTest {
    
    @Test
    void nuevoBindingOK() throws NotFoundException {
        RoleBinding nuevoRoleBinding = roleBindingOps.nuevoBinding(saraAdminMiBancolombia);
        validacionesBindings(saraAdminMiBancolombiaBD, nuevoRoleBinding);
    }

    @Test
    void nuevoBindingNotFound() {
        assertThrows(
            IllegalArgumentException.class,
            () -> roleBindingOps.nuevoBinding(new RoleBinding())
        );

        assertThrows(
            UsernameNotFoundException.class,
            () -> roleBindingOps.nuevoBinding(fernandoLectorNequiApp)
        );
    }

    @Test
    void obtenerBindingsOK() throws Exception {
        Collection<RoleBinding> bindings = roleBindingOps.obtenerBindings(sara.getUsername(), miBancolombiaBD.getScopeId());
        verify(roleBindingRepositorio).findByUsuarioAndScope(saraBD, miBancolombiaBD);
        assertEquals(1, bindings.size());
        validacionesBindings(saraAdminMiBancolombiaBD, (RoleBinding) bindings.toArray()[0]);
    }

    @Test
    void eliminarBinding() {
        assertFalse(roleBindingOps.eliminarBinding(102L));
        assertTrue(roleBindingOps.eliminarBinding(saraAdminMiBancolombiaBD.getRoleBindingId()));
        verify(roleBindingRepositorio).delete(saraAdminMiBancolombiaBD);
    }

    public void validacionesBindings(RoleBinding referencia, RoleBinding binding) {
        assertEquals(referencia.getRoleBindingId(), binding.getRoleBindingId());
        assertEquals(referencia.getAuthority(), binding.getAuthority());
        assertEquals(referencia.getScope().getAppName(), binding.getScope().getAppName());
        assertEquals(referencia.getUsuario().getUserId(), binding.getUsuario().getUserId());
        assertEquals(referencia.getScope().getScopeId(), binding.getScope().getScopeId());
        assertEquals(referencia.getRol().getRolId(), binding.getRol().getRolId());
    }

}
