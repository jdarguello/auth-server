package com.BancoC.server.auth.servicios.contratos;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import com.BancoC.server.auth.modelos.Usuario;

public interface UsuarioOps {
    Usuario crearUsuario (Usuario usuario) throws IllegalArgumentException;
    Usuario actualizarUsuario (Usuario usuario) throws NotFoundException, IllegalArgumentException;
    Boolean borrarUsuario(Long usuarioId); 
    Usuario asignarRol(String username, Long rolId, Integer scopeId) throws NotFoundException;
}
