package com.BancoC.server.auth.unitarios.modelos;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.BancoC.server.auth.GeneralTest;
import com.BancoC.server.auth.repositorios.UsusarioRepositorio;

@DataJpaTest
@ActiveProfiles("test-unitarios")
public class ModelosConfigTest extends GeneralTest {
    
    @Autowired
    protected UsusarioRepositorio repositorio;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();  //=> cargar objetos de pruebas

        adminDB = repositorio.save(admin);
    }

}
