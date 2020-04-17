package com.example.filesharing.validator;

import com.example.filesharing.domain.Credentials;
import com.example.filesharing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class CredentialsValidator implements Validator {
    @Autowired
    private UserService userService;

    public boolean supports(Class<?> aClass) {
        return Credentials.class.equals(aClass);
    }

    public void validate(Object o, Errors errors) {
        Credentials credentials = (Credentials) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");

        if (userService.findByUsername(credentials.getUsername()) == null) {
            errors.rejectValue("username", "DoesNotExist.username");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
    }
}
