package com.example.filesharing.controller;

import com.example.filesharing.domain.User;
import com.example.filesharing.form.LoginForm;
import com.example.filesharing.service.SecurityService;
import com.example.filesharing.service.UserService;
import com.example.filesharing.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @PostMapping(value = "/register", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> register(@RequestBody User user, BindingResult bindingResult) {
        HashMap<String, String> response = new HashMap<>();

        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(x -> response.put(x.getCode(), x.getDefaultMessage()));

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        userService.save(user);

        securityService.autoLogin(user.getUsername(), user.getPasswordConfirm());

        response.put("success", "success");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
