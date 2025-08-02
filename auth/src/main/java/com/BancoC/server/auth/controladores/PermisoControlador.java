package com.BancoC.server.auth.controladores;

import java.util.Collection;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.BancoC.server.auth.modelos.Permiso;
import com.BancoC.server.auth.servicios.contratos.PermisoOps;

@RestController
@RequestMapping("api/permiso")
public class PermisoControlador {

    private PermisoOps permisoService;

    public PermisoControlador(PermisoOps permisoService) {
        this.permisoService = permisoService;
    }
    
    @GetMapping("all")
    public ResponseEntity<Collection<Permiso>> getAll() {
        return ResponseEntity.ok().body(permisoService.obtenerPermisos());
    }

    @GetMapping("{id}")
    public ResponseEntity<Permiso> obtenerPorId(
        @PathVariable("id") Integer permisoId
    ) {
        try {
            Permiso permisoObtenido = this.permisoService.obtenerPorId(permisoId);
            return ResponseEntity.ok().body(permisoObtenido);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<Permiso> obtenerPorNombre(
        @RequestParam("nombre") String nombre
    ) {
        try {
            Permiso permisoObtenido = this.permisoService.obtenerPorNombre(nombre);
            return ResponseEntity.ok().body(permisoObtenido);
        } catch (NotFoundException e) {
            // TODO Auto-generated catch block
            return ResponseEntity.notFound().build();
        }
    }
}
