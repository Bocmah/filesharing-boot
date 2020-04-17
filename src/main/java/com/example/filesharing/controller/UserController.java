package com.example.filesharing.controller;

import com.example.filesharing.domain.Credentials;
import com.example.filesharing.domain.User;
import com.example.filesharing.service.LoginService;
import com.example.filesharing.service.UserService;
import com.example.filesharing.validator.CredentialsValidator;
import com.example.filesharing.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private CredentialsValidator credentialsValidator;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> register(@RequestBody User user, BindingResult bindingResult, HttpServletRequest request) {
        HashMap<String, String> response = new HashMap<>();

        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(x -> response.put(x.getCode(), x.getDefaultMessage()));

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        userService.save(user);

        response.put("success", "success");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> login(@RequestBody Credentials credentials, BindingResult bindingResult) {
        credentialsValidator.validate(credentials, bindingResult);

        HashMap<String, String> response = new HashMap<>();

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(x -> response.put(x.getCode(), x.getDefaultMessage()));

           return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        loginService.login(credentials);

        return new ResponseEntity<>(response, HttpStatus.OK);
   }
}
