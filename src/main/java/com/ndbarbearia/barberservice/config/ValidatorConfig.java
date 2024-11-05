package com.ndbarbearia.barberservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ndbarbearia.barberservice.service.UserService;
import com.ndbarbearia.barberservice.validators.UserValidator;

@Configuration
public class ValidatorConfig {

    @Autowired
    private UserService userService;

    @Bean
    public UserValidator userValidator() {
        return new UserValidator(userService);
    }

}
