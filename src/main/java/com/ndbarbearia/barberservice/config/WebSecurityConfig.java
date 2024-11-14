package com.ndbarbearia.barberservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.ndbarbearia.barberservice.model.Perfil;
import com.ndbarbearia.barberservice.service.UsuarioDetailsServiceImpl;

@Configuration
public class WebSecurityConfig {

    private final UsuarioDetailsServiceImpl usuarioDetailsServiceImpl;

    public WebSecurityConfig(UsuarioDetailsServiceImpl usuarioDetailsServiceImpl) {
        this.usuarioDetailsServiceImpl = usuarioDetailsServiceImpl;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        .authorizeHttpRequests(authorizeRequests -> authorizeRequests
            .requestMatchers("/adminlte/**", "/img/**", "/js/**", "/plugins/**").permitAll()  // Recursos públicos
            .requestMatchers("/cadastrar/**", "/edit/**", "/delete/**", "/findClient/**")
            .hasAuthority(Perfil.ADMINISTRADOR.toString())  // Apenas administradores podem acessar essas rotas
            .anyRequest().authenticated()  // Qualquer outra requisição precisa de autenticação
        )    
            .formLogin((form) -> form
            .loginPage("/login")
            .failureUrl("/login?error")  // Corrigido para usar caminho absoluto
            .permitAll()
            )
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"))  // Logout seguro com POST
                .logoutSuccessUrl("/login")
                .permitAll()
            )
            .rememberMe(rememberMe -> rememberMe
                .key("chaverememberMe")
                .userDetailsService(usuarioDetailsServiceImpl)  // Serviço de usuários para RememberMe
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public UserDetailsService usuarioDetailsService() {
        return usuarioDetailsServiceImpl;
    }
}
