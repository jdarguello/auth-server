package com.BancoC.server.auth.unitarios.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import com.BancoC.server.auth.modelos.Scope;

public class ScopeServiceTest extends ServiceConfigTest {

    @Test
    void nuevoSopeOK() {
        Scope nuevaApp = scopeOps.nuevoScope(miBancolombia);
        assertEquals(miBancolombiaBD, nuevaApp);

    }

    @Test
    void nuevoScopeIllegal() {
        assertThrows(
            IllegalArgumentException.class,
            () -> scopeOps.nuevoScope(null)
        );

        assertThrows(
            IllegalArgumentException.class,
            () -> scopeOps.nuevoScope(new Scope())
        );

        assertThrows(
            IllegalArgumentException.class,
            () -> scopeOps.nuevoScope(
                Scope.builder()
                    .appName("")
                    .build()
            )
        );

        assertThrows(
            IllegalArgumentException.class,
            () -> scopeOps.nuevoScope(
                Scope.builder()
                    .appName("algo123")
                    .owner("")
                    .build()
            )
        );
    }

    @Test
    void obtenerScopeOK() throws NotFoundException {
        Scope scopeObtenido = scopeOps.obtenerScope(miBancolombiaBD.getScopeId());
        assertEquals(miBancolombiaBD, scopeObtenido);
    }

    @Test
    void obtenerScopeNotFound() {
        assertThrows(
            NotFoundException.class,
            () -> scopeOps.obtenerScope(100)
        );
    }

    @Test
    void obtenerScopesAll() {
        validacionesScopes(scopeOps.obtenerScopes(), 2);
        verify(scopeRepositorio, times(1)).findAll();
    }

    @Test
    void obtenerScopesOwnerOK() throws NotFoundException {
        validacionesScopes(scopeOps.obtenerScopes(miBancolombia.getOwner()), 1);
        verify(scopeRepositorio, times(1)).findByOwner(miBancolombia.getOwner());
        validacionesScopes(scopeOps.obtenerScopes(nequiApp.getOwner()), 1);
        verify(scopeRepositorio, times(1)).findByOwner(nequiApp.getOwner());
    }

    @Test
    void obtenerScopesOwnerNotFound() {
        assertThrows(
            NotFoundException.class,
            () -> scopeOps.obtenerScopes("Owner inexistente")
        );
    }

    @Test
    void eliminarScope() {
        assertFalse(scopeOps.eliminarScope(10));
        verify(scopeRepositorio).findById(10);
        assertTrue(scopeOps.eliminarScope(miBancolombiaBD.getScopeId()));
        verify(scopeRepositorio).findById(miBancolombiaBD.getScopeId());
        verify(scopeRepositorio).delete(miBancolombiaBD);

    }

    private void validacionesScopes(Collection<Scope> scopes, Integer size) {
        assertEquals(size, scopes.size());
        scopes.forEach(
            scope -> assertTrue(
                scope == miBancolombiaBD || scope == nequiAppBD
            )
        );
    }
}
