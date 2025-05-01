package com.BancoC.server.auth.servicios.contratos;

import java.util.Collection;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import com.BancoC.server.auth.modelos.Rol;

public interface RolOps {
    Rol nuevoRol(Rol rol) throws IllegalArgumentException;
    Rol obtenerRol(Long rolId) throws NotFoundException;
    Rol addPermiso(Long rolId, Integer permisoId) throws NotFoundException;
    Collection<Rol> obtenerRoles();
    Boolean eliminarRol(Long rolId);
}
