package com.BancoC.server.auth.unitarios.modelos;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.BancoC.server.auth.modelos.Scope;

public class ScopeTest extends ModelosConfigTest {
 
    @Test
    void crearScope() {
        validaciones(miBancolombiaBD, miBancolombia);
        validaciones(nequiApp, nequiAppBD);
    }

    @Test
    void eliminarScope() {
        assertFalse(scopeRepositorio.findById(nequiAppBD.getScopeId()).isEmpty());
        scopeRepositorio.delete(nequiAppBD);
        assertTrue(scopeRepositorio.findById(nequiAppBD.getScopeId()).isEmpty());
    }
    

    public void validaciones(Scope referencia, Scope scope) {
        assertEquals(referencia.getAppName(), scope.getAppName());
        assertEquals(referencia.getOwner(), scope.getOwner());
        assertTrue(referencia.getScopeId() > 0);
    }
}
