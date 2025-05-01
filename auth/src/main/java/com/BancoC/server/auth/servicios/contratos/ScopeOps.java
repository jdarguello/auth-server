package com.BancoC.server.auth.servicios.contratos;

import java.util.Collection;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import com.BancoC.server.auth.modelos.Scope;

public interface ScopeOps {
    Scope nuevoScope(Scope scope) throws IllegalArgumentException;
    Scope obtenerScope(Integer scopeId) throws NotFoundException;
    Collection<Scope> obtenerScopes(String owner) throws NotFoundException;
    Collection<Scope> obtenerScopes();
    Boolean eliminarScope(Integer scopeId);
}