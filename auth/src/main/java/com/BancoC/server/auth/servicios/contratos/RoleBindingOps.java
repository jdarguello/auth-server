package com.BancoC.server.auth.servicios.contratos;

import java.util.Collection;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.BancoC.server.auth.modelos.RoleBinding;
import com.BancoC.server.auth.modelos.Usuario;

public interface RoleBindingOps {
    RoleBinding nuevoBinding(RoleBinding binding) throws UsernameNotFoundException, NotFoundException, IllegalArgumentException;
    Collection<RoleBinding> obtenerBindings(String username, Integer scopeId) throws UsernameNotFoundException, NotFoundException;
    Boolean eliminarBinding(Long bindingId);
}
