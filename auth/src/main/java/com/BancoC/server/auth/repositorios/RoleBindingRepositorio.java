package com.BancoC.server.auth.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BancoC.server.auth.modelos.RoleBinding;
import com.BancoC.server.auth.modelos.Usuario;

public interface RoleBindingRepositorio extends JpaRepository<RoleBinding, Long> {
    List<RoleBinding> findByUsuario(Usuario usuario);
}
