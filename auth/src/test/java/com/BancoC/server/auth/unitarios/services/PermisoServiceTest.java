package com.BancoC.server.auth.unitarios.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import com.BancoC.server.auth.modelos.Permiso;

public class PermisoServiceTest extends ServiceConfigTest {
    
    @Test
    void obtenerPorIdOK() throws org.springframework.data.crossstore.ChangeSetPersister.NotFoundException {
        Permiso permisoObtenido = permisoOps.obtenerPorId(readBD.getPermisoId());
        assertEquals(permisoObtenido, readBD);
    }

    @Test
    void obtenerPorIdNoEncontrado() {
        assertThrows(
            NotFoundException.class, 
            () -> permisoOps.obtenerPorId(11)
        );
    }

    @Test
    void obtenerPorNombre() throws NotFoundException {
        Permiso permisoObtenido = permisoOps.obtenerPorNombre(writeBD.getNombre());
        assertEquals(writeBD, permisoObtenido);
    }

    @Test
    void obtenerPorNombreNoEncontrado() {
        assertThrows(
            NotFoundException.class, 
            () -> permisoOps.obtenerPorNombre("Permiso inexistente")
        );
    }

    @Test
    void obtenerPermisos() {
        permisoOps.obtenerPermisos().forEach(
            permiso -> assertTrue(
                permiso == writeBD || permiso == readBD
            )
        );
    }
}
