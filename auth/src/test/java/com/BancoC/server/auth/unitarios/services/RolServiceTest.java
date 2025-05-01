package com.BancoC.server.auth.unitarios.services;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.Test;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import com.BancoC.server.auth.modelos.Rol;

public class RolServiceTest extends ServiceConfigTest {
    
    @Test
    void nuevoRolOK() {
        Rol nuevo = rolOps.nuevoRol(admin);
        assertEquals(adminBD, nuevo);
    }

    @Test
    void nuevoRolFallo() {
        assertThrows(
            IllegalArgumentException.class,
            () -> rolOps.nuevoRol(null)
        );

        assertThrows(
            IllegalArgumentException.class,
            () -> rolOps.nuevoRol(new Rol())
        );

        assertThrows(
            IllegalArgumentException.class,
            () -> rolOps.nuevoRol(
                Rol.builder()
                    .nombre("")
                .build()
            )
        );
    }

    @Test
    void obtenerPermisoOK() throws NotFoundException {
        Rol rolObtenido = rolOps.obtenerRol(adminBD.getRolId());
        assertEquals(adminBD, rolObtenido);
    }

    @Test
    void obtenerPermisoNotFound() {
        assertThrows(
            NotFoundException.class,
            () -> rolOps.obtenerRol(200L)
        );
    }

    @Test
    void addPermisoOK() throws NotFoundException {
        Rol rolModificado = rolOps.addPermiso(
                adminBD.getRolId(), readBD.getPermisoId());
            
        verify(rolRepositorio).save(rolModificado);
        assertEquals(1, rolModificado.getPermisos().size());
        assertEquals(readBD, rolModificado.getPermisos().toArray()[0]);
    }

    @Test
    void addPermisoNotFound() {
        //Rol NotFound
        assertThrows(
            NotFoundException.class,
            () -> rolOps.addPermiso(100L, readBD.getPermisoId())
        );

        //Permiso NotFound
        assertThrows(
            NotFoundException.class,
            () -> rolOps.addPermiso(adminBD.getRolId(), 100)
        );

        verify(rolRepositorio, times(0)).save(adminBD);
    }

    @Test
    void obtenerRoles() {
        rolOps.obtenerRoles().forEach(
            rol -> assertTrue(
                rol == adminBD || rol == readerBD 
            )
        );
    }

    @Test
    void eliminarRol() {
        assertFalse(rolOps.eliminarRol(112L));
        assertTrue(rolOps.eliminarRol(adminBD.getRolId()));
        verify(rolRepositorio, times(1)).delete(adminBD);
    }
}
