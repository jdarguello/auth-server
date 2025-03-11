package com.BancoC.server.auth.modelos;

import org.springframework.security.core.GrantedAuthority;

public class Rol implements GrantedAuthority {

    @Override
    public String getAuthority() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAuthority'");
    }

    public Boolean otraCosa() {
        return false;
    }
    
}
