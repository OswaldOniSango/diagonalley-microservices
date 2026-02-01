package com.oswaldo.users.controller;

import com.oswaldo.users.repository.UserRepository;
import com.oswaldo.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/me")
    public Map<String, Object> me(Authentication auth) {
        return userService.me(auth);
    }
}
