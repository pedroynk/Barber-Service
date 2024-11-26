package com.ndbarbearia.barberservice.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ndbarbearia.barberservice.model.Usuario;
import com.ndbarbearia.barberservice.repository.UsuarioRepository;
import com.ndbarbearia.barberservice.service.UsuarioService;
import com.ndbarbearia.barberservice.utils.AlterarSenhaUtils;
import com.ndbarbearia.barberservice.utils.SenhaUtils;
    
@Controller
public class LoginController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/login")
    public String login() {
        return "usuario/login";
    }

    @GetMapping("/perfil")
    public ModelAndView perfil(Principal principal) {
        ModelAndView modelAndView = new ModelAndView("usuario/perfil");

        modelAndView.addObject("logado", this.usuarioService.searchByEmail(principal.getName()).get());
        modelAndView.addObject("idUsuario", this.usuarioService.searchByEmail(principal.getName()).get().getId());
        modelAndView.addObject("alterarSenhaForm", new AlterarSenhaUtils());

        return modelAndView;
    }

    @PostMapping("/alterar-senha")
    public String alterarSenha(AlterarSenhaUtils form, Principal principal) {
        Usuario usuario = usuarioRepository.findByEmail(principal.getName()).get();

        if (SenhaUtils.matches(form.getSenhaAtual(), usuario.getSenha())) {
            usuario.setSenha(SenhaUtils.encode(form.getNovaSenha()));

            usuarioRepository.save(usuario);
        }


        return "redirect:/perfil";

    }
}