package com.ndbarbearia.barberservice.validators;

import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.ndbarbearia.barberservice.model.User;
import com.ndbarbearia.barberservice.service.UserService;

public class UserValidator implements Validator {
        private static final String PASSWORD_PATTERN = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d@#$%^&+=]{5,}$";

    public UserValidator(UserService userService) {
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        User user = (User) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty");
        if (user.getName().length() < 3) {
            errors.rejectValue("name", "Size.user.name.min");
        } else if (user.getName().length() > 80) {
            errors.rejectValue("name", "Size.user.name.max");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");
        if (!user.getEmail().matches("[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}")) {
            errors.rejectValue("email", "Invalid.user.email");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "profile", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (!Pattern.matches(PASSWORD_PATTERN, user.getPassword())) {
            errors.rejectValue("password", "Pattern.user.senha");
        }
    }

}