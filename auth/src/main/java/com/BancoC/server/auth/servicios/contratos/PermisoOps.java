package com.BancoC.server.auth.servicios.contratos;

import java.util.Collection;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import com.BancoC.server.auth.modelos.Permiso;

public interface PermisoOps {
    Permiso obtenerPorId(Integer permisoId) throws NotFoundException;
    Permiso obtenerPorNombre(String nombre) throws NotFoundException;
    Collection<Permiso> obtenerPermisos();
}
