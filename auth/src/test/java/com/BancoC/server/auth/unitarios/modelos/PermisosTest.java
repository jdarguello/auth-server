package com.BancoC.server.auth.unitarios.modelos;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.BancoC.server.auth.modelos.Permiso;

public class PermisosTest extends ModelosConfigTest {
    
    @Test
    void crearPermiso() {
        validaciones(read, readBD);
    }

   @Test
   void obtenerPermiso() {
    //Permiso permisoObtenido = permisoRepositorio.findByRol();
   } 

    @Test
    void borrarPermiso() {

    }

    public void validaciones(Permiso referencia, Permiso permiso) {
        assertEquals(referencia.getNombre(), permiso.getNombre());
        assertEquals(referencia.getDescripcion(), permiso.getDescripcion());
        assertTrue(referencia.getPermisoId() > 0);
    }
}
