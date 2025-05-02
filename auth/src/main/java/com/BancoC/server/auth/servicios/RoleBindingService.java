package com.BancoC.server.auth.servicios;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.BancoC.server.auth.modelos.Rol;
import com.BancoC.server.auth.modelos.RoleBinding;
import com.BancoC.server.auth.modelos.Scope;
import com.BancoC.server.auth.modelos.Usuario;
import com.BancoC.server.auth.repositorios.RoleBindingRepositorio;
import com.BancoC.server.auth.servicios.contratos.RolOps;
import com.BancoC.server.auth.servicios.contratos.RoleBindingOps;
import com.BancoC.server.auth.servicios.contratos.ScopeOps;

@Service
public class RoleBindingService implements RoleBindingOps {

    private final UserDetailsService userService;
    private final RolOps rolService;
    private final ScopeOps scopeService;

    private final RoleBindingRepositorio repo;

    public RoleBindingService(UserDetailsService userService, 
                              RoleBindingRepositorio repo,
                              RolOps rolService,
                              ScopeOps scopeService) {
        this.userService = userService;
        this.repo = repo;
        this.rolService = rolService;
        this.scopeService = scopeService;
    }


    @Override
    public RoleBinding nuevoBinding(RoleBinding binding) throws UsernameNotFoundException, NotFoundException, IllegalArgumentException {
        validacionReferencias(binding);
        return repo.save(binding);
    }

    @Override
    public Collection<RoleBinding> obtenerBindings(String username, Integer scopeId) throws UsernameNotFoundException, NotFoundException {
        Usuario usuario = (Usuario) userService.loadUserByUsername(username);
        Scope scope = scopeService.obtenerScope(scopeId);
        return repo.findByUsuarioAndScope(usuario, scope);
    }

    @Override
    public Boolean eliminarBinding(Long bindingId) {
        Boolean response = false;
        response = validacionBinding(bindingId, response);
        return response;
    }

    private Boolean validacionBinding(Long bindingId, Boolean response) {
        Optional<RoleBinding> binding = repo.findById(bindingId);
        if (!binding.isEmpty()) {
            repo.delete(binding.get());
            response = true;
        }
        return response;
    }

    private void validacionReferencias(RoleBinding binding) throws UsernameNotFoundException, NotFoundException, IllegalArgumentException {
        validacionesUsuario(binding.getUsuario());
        validacionesRol(binding.getRol());
        validacionesScope(binding.getScope());
        
    }

    private void validacionesUsuario(UserDetails usuario) throws UsernameNotFoundException, IllegalArgumentException {
        if (usuario == null || usuario.getUsername() == null) {
            throw new IllegalArgumentException();
        } 
        usuario = userService.loadUserByUsername(usuario.getUsername());  //Will fail if nothing is found
    }

    private void validacionesRol(Rol rol) throws IllegalArgumentException, NotFoundException {
        if (rol == null || rol.getRolId() == null) {
            throw new IllegalArgumentException();
        }
        rol = rolService.obtenerRol(rol.getRolId());
    }

    private void validacionesScope(Scope scope) throws NotFoundException, IllegalArgumentException {
        if (scope == null || scope.getScopeId() == null) {
            throw new IllegalArgumentException();
        }
        scope = scopeService.obtenerScope(scope.getScopeId());
    }
    
}
