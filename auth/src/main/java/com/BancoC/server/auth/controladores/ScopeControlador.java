package com.BancoC.server.auth.controladores;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BancoC.server.auth.modelos.Scope;
import com.BancoC.server.auth.servicios.contratos.ScopeOps;

@RestController
@RequestMapping("api/scope")
public class ScopeControlador {
    
    private final ScopeOps scopeService;

    public ScopeControlador(ScopeOps scopeService) {
        this.scopeService = scopeService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Scope> obtenerPorId(
        @PathVariable("id") Integer scopeId
    ) {
        try {
            Scope scopeObtenido = this.scopeService.obtenerScope(scopeId);
            return ResponseEntity.ok().body(scopeObtenido);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> crear(
        @RequestBody Scope scope
    ) {
        Scope scopeCreado = this.scopeService.nuevoScope(scope);
        if (scopeCreado != null | scopeCreado.getScopeId() != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(scopeCreado);
        }
        return ResponseEntity.internalServerError().body("Error en creación del scope. Scope = " + scope.toString());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> borrar(
        @PathVariable("id") Integer scopeId
    ) {
        this.scopeService.eliminarScope(scopeId);
        return ResponseEntity.ok().build();
    }
}
