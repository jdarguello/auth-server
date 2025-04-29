package com.BancoC.server.auth.unitarios.modelos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;

import com.BancoC.server.auth.modelos.Usuario;

public class UsuariosTest extends ModelosConfigTest {

    @Test
    void createUser() {
        validaciones(fernandoBD, fernando);
    }

    @Test
    void loadUserByUsername() {
        UserDetails usuarioObtenido = ususarioRepositorio.findByUsername("admin@gmail.com");
        validaciones(fernandoBD, usuarioObtenido);
    }

    private void validaciones(Usuario referencia, UserDetails usuario) {
        assertEquals(referencia.getUsername(), usuario.getUsername());
        assertEquals(referencia.getPassword(), usuario.getPassword());
    }
}
