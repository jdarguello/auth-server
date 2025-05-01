package com.BancoC.server.auth.servicios;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.BancoC.server.auth.modelos.Scope;
import com.BancoC.server.auth.repositorios.ScopeRepositorio;
import com.BancoC.server.auth.servicios.contratos.ScopeOps;

@Service
public class ScopeService implements ScopeOps{

    private ScopeRepositorio repo;

    public ScopeService(ScopeRepositorio repo) {
        this.repo = repo;
    }

    @Override
    public Scope nuevoScope(Scope scope) throws IllegalArgumentException {
        if (scope == null || scope.getAppName() == null || 
            scope.getOwner() == null || scope.getAppName().equals("") ||
            scope.getOwner().equals("")) {
                
            throw new IllegalArgumentException();
        }
        return repo.save(scope);
    }

    @Override
    public Scope obtenerScope(Integer scopeId) throws NotFoundException {
        Optional<Scope> scopeObtenido = repo.findById(scopeId);
        if (scopeObtenido.isEmpty()) {
            throw new NotFoundException();
        }
        return scopeObtenido.get();
    }

    @Override
    public Boolean eliminarScope(Integer scopeId) {
        Boolean response = false;
        try {
            Scope scopeObtenido = this.obtenerScope(scopeId);
            repo.delete(scopeObtenido);
            response = true;
        } catch (NotFoundException exception) {}
        return response;
    }

    @Override
    public Collection<Scope> obtenerScopes(String owner) throws NotFoundException {
        Collection<Scope> listaScopes = repo.findByOwner(owner);
        if (listaScopes == null || listaScopes.isEmpty()) {
            throw new NotFoundException();
        }
        return listaScopes;
    }

    @Override
    public Collection<Scope> obtenerScopes() {
        return repo.findAll();
    }
    
}
