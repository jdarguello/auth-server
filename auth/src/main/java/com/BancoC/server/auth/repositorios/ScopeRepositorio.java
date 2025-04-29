package com.BancoC.server.auth.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BancoC.server.auth.modelos.Scope;

public interface ScopeRepositorio extends JpaRepository<Scope, Integer>{
    
}
