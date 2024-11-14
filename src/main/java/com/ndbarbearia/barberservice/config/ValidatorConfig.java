package com.ndbarbearia.barberservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ndbarbearia.barberservice.service.UsuarioService;
import com.ndbarbearia.barberservice.validators.UsuarioValidator;

@Configuration
public class ValidatorConfig {

    @Autowired
    private UsuarioService usuarioService;

    @Bean
    public UsuarioValidator usuarioValidator() {
        return new UsuarioValidator(usuarioService);
    }

}
