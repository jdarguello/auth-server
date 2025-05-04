package com.BancoC.server.auth.servicios;

import java.util.Collection;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.BancoC.server.auth.modelos.Rol;
import com.BancoC.server.auth.modelos.RoleBinding;
import com.BancoC.server.auth.modelos.Scope;
import com.BancoC.server.auth.modelos.Usuario;
import com.BancoC.server.auth.repositorios.UsuarioRepositorio;
import com.BancoC.server.auth.servicios.contratos.RolOps;
import com.BancoC.server.auth.servicios.contratos.RoleBindingOps;
import com.BancoC.server.auth.servicios.contratos.ScopeOps;
import com.BancoC.server.auth.servicios.contratos.UsuarioOps;

@Service
public class UsuarioService implements UsuarioOps {

    private final RoleBindingOps roleBindingOps;
    private final ScopeOps scopeOps;
    private final RolOps rolOps;
    private final UsuarioRepositorio repo;

    public UsuarioService (RoleBindingOps roleBindingOps, ScopeOps scopeOps,
            RolOps rolOps, UsuarioRepositorio repo) {
        this.roleBindingOps = roleBindingOps;
        this.scopeOps = scopeOps;
        this.rolOps = rolOps;
        this.repo = repo;
    }

    @Override
    public void createUser(UserDetails user) {
        repo.save((Usuario) user);
    }

    @Override
    public void updateUser(UserDetails user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateUser'");
    }

    @Override
    public void deleteUser(String username) {
        if (userExists(username)) {
            repo.delete((Usuario) loadUserByUsername(username));
        }
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'changePassword'");
    }

    @Override
    public boolean userExists(String username) {
        boolean response = false;
        try {
            loadUserByUsername(username);
            response = true;
        } catch (UsernameNotFoundException exception) {}
        return response;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails usuario = repo.findByUsername(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("404: username=" + username + " NOT FOUND");
        }
        return usuario;
    }

    @Override
    public Usuario asignarRol(String username, Long rolId, Integer scopeId) throws NotFoundException {
        //Cargar info
        Usuario usuario = (Usuario) this.loadUserByUsername(username);
        Scope scope = scopeOps.obtenerScope(scopeId);
        Rol rol = rolOps.obtenerRol(rolId);
        Collection<RoleBinding> bindings = roleBindingOps.obtenerBindings(username, scopeId);

        //Asignación de rol
        bindings.add(roleBindingOps.nuevoBinding(
            RoleBinding.builder()
                .rol(rol)
                .scope(scope)
                .usuario(usuario)
            .build()
        ));
        usuario.setAuthorities(bindings.stream().toList());
        return usuario;
    }
    
}
