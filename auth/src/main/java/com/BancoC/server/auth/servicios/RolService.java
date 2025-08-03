package com.BancoC.server.auth.servicios;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.BancoC.server.auth.modelos.Permiso;
import com.BancoC.server.auth.modelos.Rol;
import com.BancoC.server.auth.repositorios.RolRepositorio;
import com.BancoC.server.auth.servicios.contratos.PermisoOps;
import com.BancoC.server.auth.servicios.contratos.RolOps;

@Service
public class RolService implements RolOps {

    private RolRepositorio repo;
    private PermisoOps service;

    public RolService(RolRepositorio repo, PermisoOps service) {
        this.repo = repo;
        this.service = service;
    }

    @Override
    public Rol nuevoRol(Rol rol) throws IllegalArgumentException {
        if (rol == null || rol.getNombre() == null || rol.getNombre().equals("")) {
            throw new IllegalArgumentException();
        }
        return repo.save(rol);
    }

    @Override
    public Rol obtenerRol(Long rolId) throws NotFoundException {
        Optional<Rol> rolObtenido = repo.findById(rolId);
        if (rolObtenido.isEmpty()) {
            throw new NotFoundException();
        }
        return rolObtenido.get();
    }

    @Override
    public Rol addPermiso(Long rolId, Integer permisoId) throws NotFoundException, IllegalArgumentException {
        Rol rol = this.obtenerRol(rolId);
        Permiso permiso = service.obtenerPorId(permisoId);
        rol.getPermisos().add(permiso);
        this.nuevoRol(rol);
        return rol;
    }

    @Override
    public Collection<Rol> obtenerRoles() {
        return repo.findAll();
    }

    @Override
    public Boolean eliminarRol(Long rolId) {
        Boolean response = false;
        try {
            Rol rol = this.obtenerRol(rolId);
            repo.delete(rol);
            response = true;
        } catch(NotFoundException exception) {}
        return response;
    }
    
}
