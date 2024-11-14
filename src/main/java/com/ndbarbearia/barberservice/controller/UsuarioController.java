package com.ndbarbearia.barberservice.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ndbarbearia.barberservice.dto.AlertDTO;
import com.ndbarbearia.barberservice.model.Usuario;
import com.ndbarbearia.barberservice.service.UsuarioService;
import com.ndbarbearia.barberservice.validators.UsuarioValidator;


@Controller
@RequestMapping("usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioValidator usuarioValidator;

    @GetMapping("/listar")
    public ModelAndView list(Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("usuario/lista");

        Page<Usuario> usuariosPage = this.usuarioService.listAllUsers(pageable);

        usuariosPage.forEach(usuario -> {
            BindingResult result = new BeanPropertyBindingResult(usuario, "usuario");
            usuarioValidator.validate(usuario, result);
        });

        modelAndView.addObject("usuarios", usuariosPage);

        return modelAndView;
    }

    @GetMapping("/cadastrar")
    public ModelAndView cadastrar() {

        ModelAndView modelAndView = new ModelAndView("usuario/formulario");
        modelAndView.addObject("tiposDeUsuarios", this.usuarioService.listUsersTypes());
        modelAndView.addObject("usuario", new Usuario());

        return modelAndView;
    }

    @PostMapping("/cadastrar")
    public String cadastrar(@Validated Usuario usuario, BindingResult bindingResult, ModelMap model,
            RedirectAttributes attrs, @RequestParam("fotoPerfil") MultipartFile fotoPerfil) throws IOException {

        UsuarioValidator usuarioValidator = new UsuarioValidator(usuarioService);
        usuarioValidator.validate(usuario, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("tipoDeUsuarios", this.usuarioService.listUsersTypes());
            model.addAttribute("usuario", usuario);
            return "usuario/formulario";
        }

        if (fotoPerfil != null && !fotoPerfil.isEmpty()) {
            String filePath = this.usuarioService.saveProfilePicture(fotoPerfil);
            usuario.setFotoPerfilPath(filePath);
        }

        if (usuario.getId() == null) {
            usuario.setAtivo(true);
            usuarioService.register(usuario);
        } else {
            usuario.setAtivo(true);
            usuarioService.register(usuario);

        }

        attrs.addFlashAttribute("alert", new AlertDTO("Usuário cadastrado com sucesso!", "alert-success"));

        return "redirect:/usuarios/listar";
    }

    @GetMapping("/{id}/editar")
    public ModelAndView editar(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("usuario/formulario");

        modelAndView.addObject("tiposDeUsuarios", this.usuarioService.listUsersTypes());
        modelAndView.addObject("usuario", usuarioService.searchById(id));

        return modelAndView;
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        usuarioService.searchAndDeleteById(id);

        return "redirect:/usuarios/listar";
    }

    @GetMapping("/{id}")
    public ModelAndView detalhes(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("usuario/detalhes");

        modelAndView.addObject("usuario", usuarioService.searchById(id));

        return modelAndView;
    }

    @GetMapping("/ativar/{id}")
    public String active(@PathVariable Long id) {
        usuarioService.ativarOuDesativar(id);
        return "redirect:/usuarios/listar";
    }
}
