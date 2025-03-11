package com.BancoC.server.auth;

import org.junit.jupiter.api.BeforeEach;

import com.BancoC.server.auth.modelos.Usuario;

public class GeneralTest {
    
    protected Usuario sara;
    protected Usuario admin;

    protected Usuario adminDB;

    @BeforeEach
    public void setUp() {
        sara = Usuario.builder()
            .username("sara@gmail.com")
            .password("super-secret123")
            .build();
        
        admin = Usuario.builder()
            .username("admin@gmail.com")
            .password("secret123")
            .build();
    }

}
