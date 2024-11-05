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
import com.ndbarbearia.barberservice.model.User;
import com.ndbarbearia.barberservice.service.UserService;
import com.ndbarbearia.barberservice.validators.UserValidator;


@Controller
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    @GetMapping("/list")
    public ModelAndView list(Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("user/list");

        Page<User> usersPage = this.userService.listAllUsers(pageable);

        usersPage.forEach(user -> {
            BindingResult result = new BeanPropertyBindingResult(user, "user");
            userValidator.validate(user, result);
        });

        modelAndView.addObject("users", usersPage);

        return modelAndView;
    }

    @GetMapping("/cadastrar")
    public ModelAndView cadastrar() {

        ModelAndView modelAndView = new ModelAndView("usuario/formulario");
        modelAndView.addObject("tiposDeUsuarios", this.userService.listUsersTypes());
        modelAndView.addObject("usuario", new User());

        return modelAndView;
    }

    @PostMapping("/register")
    public String register(@Validated User user, BindingResult bindingResult, ModelMap model,
            RedirectAttributes attrs, @RequestParam("profilePicture") MultipartFile profilePicture) throws IOException {

        UserValidator userValidator = new UserValidator(userService);
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
           
            model.addAttribute("usersType", this.userService.listUsersTypes());
            model.addAttribute("users", user);
            return "users/formulario";
        }

        if (profilePicture != null && !profilePicture.isEmpty()) {
            String filePath = this.userService.saveProfilePicture(profilePicture);
            user.setProfilePicture(filePath);
        }

        if (user.getId() == null) {
            user.setActive(true);
            userService.register(user);
        } else {
            user.setActive(true);
            userService.register(user);

        }

        attrs.addFlashAttribute("alert", new AlertDTO("Usuário cadastrado com sucesso!", "alert-success"));

        return "redirect:/usuarios/listar";
    }

    @GetMapping("/{id}/editar")
    public ModelAndView editar(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("usuario/formulario");

        modelAndView.addObject("tiposDeUsuarios", this.userService.listUsersTypes());
        modelAndView.addObject("usuario", userService.searchById(id));

        return modelAndView;
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        userService.searchAndDeleteById(id);

        return "redirect:/usuarios/listar";
    }

    @GetMapping("/{id}")
    public ModelAndView detalhes(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("user/details");

        modelAndView.addObject("user", userService.searchById(id));

        return modelAndView;
    }

    @GetMapping("/active/{id}")
    public String active(@PathVariable Long id) {
        userService.activeOrDisable(id);
        return "redirect:/users/list";
    }
}
