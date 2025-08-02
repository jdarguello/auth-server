package com.BancoC.server.auth.controladores;

import java.util.Collection;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BancoC.server.auth.modelos.Rol;
import com.BancoC.server.auth.servicios.contratos.RolOps;

@RestController
@RequestMapping("api/rol")
public class RolControlador {

    private final RolOps rolOps;

    public RolControlador(RolOps rolOps) {
        this.rolOps = rolOps;
    }
    
    @GetMapping("all")
    public ResponseEntity<Collection<Rol>> getAll () {
        return ResponseEntity.ok().body(rolOps.obtenerRoles());
    }

    @GetMapping("{id}")
    public ResponseEntity<Rol> obtenerPorId(
        @PathVariable("id") Long rolId
    ) {
        try {
            Rol rolObtenido = this.rolOps.obtenerRol(rolId);
            return ResponseEntity.ok().body(rolObtenido);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("nuevo")
    public ResponseEntity<Rol> crear(
        @RequestBody Rol rol
    ) {
        Rol rolCreado = this.rolOps.nuevoRol(rol);
        return ResponseEntity.status(HttpStatus.CREATED).body(rolCreado);
    }


}
