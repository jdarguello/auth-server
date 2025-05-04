package com.BancoC.server.auth.servicios.contratos;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;

import com.BancoC.server.auth.modelos.Usuario;

public interface UsuarioOps extends UserDetailsManager {
    Usuario asignarRol(String username, Long rolId, Integer scopeId) throws NotFoundException;
}
