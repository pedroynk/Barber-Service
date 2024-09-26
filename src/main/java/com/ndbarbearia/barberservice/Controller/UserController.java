package com.ndbarbearia.barberservice.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller


public class UserController {

    @GetMapping("/users")
    public String getMethodName(@RequestParam String param) {
        return new String();
    }
    
    public String UserController() {
        System.out.println("Barber Service on!");
        return "barberservice";
    }
}
