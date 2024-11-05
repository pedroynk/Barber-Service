package com.ndbarbearia.barberservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ndbarbearia.barberservice.model.User;
import com.ndbarbearia.barberservice.model.Profile;
import com.ndbarbearia.barberservice.service.UserService;

@SpringBootApplication
public class BarberserviceApplication implements CommandLineRunner {

    @Value("${user.name}")
    private String userName;

    @Value("${user.email}")
    private String userEmail;

    @Value("${user.password}")
    private String userPassword;

    @Value("${user.profile}")
    private String userProfile;

    @Autowired
    private UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(BarberserviceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (userService.searchAll().isEmpty()) {
            Profile profileEnum = Profile.valueOf(userProfile.toUpperCase());

            User user = User.builder()
                    .name(userName)
                    .email(userEmail)
                    .password(userPassword)
                    .profile(profileEnum)
                    .active(true)
                    .build();

            userService.register(user);
        }
    }
}
