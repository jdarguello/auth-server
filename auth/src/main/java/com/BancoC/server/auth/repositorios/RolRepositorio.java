package com.BancoC.server.auth.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BancoC.server.auth.modelos.Rol;

public interface RolRepositorio extends JpaRepository<Rol, Long>{
    
}
