package com.BancoC.server.auth.unitarios.modelos;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.BancoC.server.auth.modelos.Permiso;
import com.BancoC.server.auth.modelos.Rol;

public class RolTest extends ModelosConfigTest{
    
    @Test
    void crearRol() {
        validaciones(readerBD, reader);
        validaciones(adminBD, admin);
        validaciones(writerAndReaderBD, writerAndReader);
    }

    @Test
    void obtencionPermisos() {
        Rol adminRol = rolRepositorio.findById(adminBD.getRolId()).get();

        validaciones(adminBD, adminRol);
        validacionPermisos(adminRol, List.of(
            readBD, writeBD, updateBD
        ));
    }

    @Test
    void eliminarRol() {
        assertFalse(rolRepositorio.findById(adminBD.getRolId()).isEmpty());

        rolRepositorio.delete(adminBD);
        assertTrue(rolRepositorio.findById(adminBD.getRolId()).isEmpty());
    }

    public void validaciones(Rol referencia, Rol rol) {
        assertEquals(referencia.getNombre(), rol.getNombre());
        assertEquals(referencia.getPermisos().size(), rol.getPermisos().size());
    }

    public void validacionPermisos(Rol rol, List<Permiso> permisos) {
        rol.getPermisos().forEach(
            permisoRol -> {
                Boolean response = false;
                for (Permiso permiso : permisos) {
                    if (permiso == permisoRol) {
                        response = true;
                        break;
                    }
                }
                assertTrue(response);
            }
        );
    }

}
