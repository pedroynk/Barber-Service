package com.ndbarbearia.barberservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ndbarbearia.barberservice.model.Usuario;
import com.ndbarbearia.barberservice.model.Perfil;
import com.ndbarbearia.barberservice.service.UsuarioService;

@SpringBootApplication
public class BarberserviceApplication implements CommandLineRunner {

    @Value("${usuario.nome}")
    private String usuarioNome;

    @Value("${usuario.email}")
    private String usuarioEmail;

    @Value("${usuario.senha}")
    private String usuarioSenha;

    @Value("${usuario.perfil}")
    private String usuarioPerfil;

    @Autowired
    private UsuarioService usuarioService;

    public static void main(String[] args) {
        SpringApplication.run(BarberserviceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (usuarioService.searchAll().isEmpty()) {
            Perfil perfil = Perfil.valueOf(usuarioPerfil.toUpperCase());

            Usuario usuario = Usuario.builder()
                    .nome(usuarioNome)
                    .email(usuarioEmail)
                    .senha(usuarioSenha)
                    .perfil(perfil)
                    .ativo(true)
                    .build();

                    usuarioService.register(usuario);
        }
    }
}
