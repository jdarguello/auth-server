package com.BancoC.server.auth.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.BancoC.server.auth.modelos.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long>  {

    UserDetails findByUsername(String username);
}
