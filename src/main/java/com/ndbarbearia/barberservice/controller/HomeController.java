package com.ndbarbearia.barberservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    @GetMapping()
    public ModelAndView home(/*Principal principal*/) {
        ModelAndView modelAndView = new ModelAndView("home/home");
        // modelAndView.addObject("principal", principal);
        return modelAndView;
    }
}
