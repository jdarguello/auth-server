package com.BancoC.server.auth.unitarios.modelos;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;

import com.BancoC.server.auth.modelos.Usuario;

public class UsuariosTest extends ModelosConfigTest {

    @Test
    void createUser() {
        validaciones(adminDB, admin);
    }

    @Test
    void getUser() {
        Optional<Usuario> usuarioObtenido = repositorio.findById(adminDB.getUserId());

        validaciones(adminDB, usuarioObtenido.get());
    }

    @Test
    void loadUserByUsername() {
        UserDetails usuarioObtenido = repositorio.findByUsername("admin@gmail.com");

        validaciones(adminDB, usuarioObtenido);
    }

    private void validaciones(Usuario referencia, UserDetails usuario) {
        assertEquals(referencia.getUsername(), usuario.getUsername());
        assertEquals(referencia.getPassword(), usuario.getPassword());
    }
}
