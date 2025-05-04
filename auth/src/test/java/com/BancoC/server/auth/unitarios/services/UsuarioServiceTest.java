package com.BancoC.server.auth.unitarios.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.BancoC.server.auth.modelos.RoleBinding;
import com.BancoC.server.auth.modelos.Usuario;

public class UsuarioServiceTest extends ServiceConfigTest {
    
    @Test
    void crearUsuario() {
        usuarioOps.createUser(sara);
        verify(usuarioRepositorio).save(sara);

        usuarioOps.createUser(fernando);
        verify(usuarioRepositorio).save(fernando);
    }

    @Test
    void loadByUserNameOK() {
        Usuario usuarioObtenido = (Usuario) usuarioOps.loadUserByUsername(sara.getUsername());
        validacionesUsuario(saraBD, usuarioObtenido);
    }

    @Test
    void loadByUserNameNotFound() {
        Exception exception = assertThrows(
            UsernameNotFoundException.class,
            () -> usuarioOps.loadUserByUsername(fernando.getUsername())
        );

        assertEquals("404: username=admin@gmail.com NOT FOUND", exception.getMessage());
    }

    @Test
    void userExists() {
        assertFalse(usuarioOps.userExists(fernando.getUsername()));
        assertTrue(usuarioOps.userExists(sara.getUsername()));
        verify(usuarioRepositorio).findByUsername(sara.getUsername());
    }

    @Test
    void deleteUser() {
        usuarioOps.deleteUser(sara.getUsername());
        verify(usuarioRepositorio).delete(saraBD);

        usuarioOps.deleteUser(fernando.getUsername());
        verify(usuarioRepositorio, times(0)).delete(fernandoBD);
    }

    @Test
    void asignarRolOK() throws NotFoundException {
        assertEquals(0, saraBD.getAuthorities().size());

        Usuario usuario = usuarioOps.asignarRol(
            sara.getUsername(), adminBD.getRolId(), nequiAppBD.getScopeId()
        );

        validacionesUsuario(saraBD, usuario);
        verify(mockRoleBindingOps).nuevoBinding(
            RoleBinding.builder()
                .rol(adminBD)
                .scope(nequiAppBD)
                .usuario(saraBD)
            .build()
        );
        assertEquals(1, usuario.getAuthorities().size());
        assertEquals(adminBD, ((RoleBinding) usuario.getAuthorities().toArray()[0]).getRol());
    }

    private void validacionesUsuario(Usuario referencia, Usuario usuario) {
        assertEquals(referencia.getUserId(), usuario.getUserId());
        assertEquals(referencia.getUsername(), usuario.getUsername());
    }

}
