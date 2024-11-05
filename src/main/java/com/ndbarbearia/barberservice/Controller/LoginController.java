package com.ndbarbearia.barberservice.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ndbarbearia.barberservice.model.User;
import com.ndbarbearia.barberservice.repository.UserRepository;
import com.ndbarbearia.barberservice.service.UserService;
import com.ndbarbearia.barberservice.utils.ChangePasswordUtils;
import com.ndbarbearia.barberservice.utils.PasswordUtils;
    
@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("login")
    public String login() {
        return "user/login";
    }

    @GetMapping("/profile")
    public ModelAndView profile(Principal principal) {
        ModelAndView modelAndView = new ModelAndView("user/profile");

        modelAndView.addObject("loged", this.userService.searchByEmail(principal.getName()).get());
        modelAndView.addObject("idUser", this.userService.searchByEmail(principal.getName()).get().getId());
        modelAndView.addObject("changePasswordForm", new ChangePasswordUtils());

        return modelAndView;
    }

    @PostMapping("/alterar-senha")
    public String changePassword(ChangePasswordUtils form, Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).get();

        if (PasswordUtils.matches(form.getCurrentPassword(), user.getPassword())) {
            user.setPassword(PasswordUtils.encode(form.getNewPassword()));

            userRepository.save(user);
        }

        return "redirect:/profile";

    }


    
}
