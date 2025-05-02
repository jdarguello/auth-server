package com.BancoC.server.auth.repositorios;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BancoC.server.auth.modelos.RoleBinding;
import com.BancoC.server.auth.modelos.Usuario;
import com.BancoC.server.auth.modelos.Scope;


public interface RoleBindingRepositorio extends JpaRepository<RoleBinding, Long> {
    Collection<RoleBinding> findByUsuarioAndScope(Usuario usuario, Scope scope);
}
