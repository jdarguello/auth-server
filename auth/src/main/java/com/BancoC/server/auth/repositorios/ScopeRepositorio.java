package com.BancoC.server.auth.repositorios;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BancoC.server.auth.modelos.Scope;

public interface ScopeRepositorio extends JpaRepository<Scope, Integer>{
    Collection<Scope> findByOwner(String owner);
}
