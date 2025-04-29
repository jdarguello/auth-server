package com.BancoC.server.auth.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BancoC.server.auth.modelos.Permiso;

public interface PermisoRepositorio extends JpaRepository<Permiso, Integer> {
    
}
