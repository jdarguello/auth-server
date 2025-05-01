package com.BancoC.server.auth.servicios;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.BancoC.server.auth.modelos.Permiso;
import com.BancoC.server.auth.repositorios.PermisoRepositorio;
import com.BancoC.server.auth.servicios.contratos.PermisoOps;

@Service
public class PermisoService implements PermisoOps{

    private PermisoRepositorio repo;

    public PermisoService(PermisoRepositorio repo) {
        this.repo = repo;
    }

    @Override
    public Permiso obtenerPorId(Integer permisoId) throws NotFoundException {
        Optional<Permiso> permisoObtenido = repo.findById(permisoId);
        exception(permisoObtenido);
        return permisoObtenido.get();
    }

    @Override
    public Permiso obtenerPorNombre(String nombre) throws NotFoundException {
        Optional<Permiso> permisoObtenido = repo.findByNombre(nombre);
        exception(permisoObtenido);
        return permisoObtenido.get();
    }

    @Override
    public Collection<Permiso> obtenerPermisos() {
        return repo.findAll();
    }

    private void exception(Optional<Permiso> permiso) throws NotFoundException {
        if (permiso.isEmpty()) {
            throw new NotFoundException();
        }
    }
    
}
