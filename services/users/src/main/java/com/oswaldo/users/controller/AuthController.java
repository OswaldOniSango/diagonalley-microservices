package com.oswaldo.users.controller;

import com.oswaldo.common.security.JwtUtil;
import com.oswaldo.users.model.User;
import com.oswaldo.users.repository.UserRepository;
import com.oswaldo.users.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    UserService userService;


    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest req) {
        return userService.registerUser(req);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req) {
        return userService.login(req);

    }

    public record RegisterRequest(@NotBlank String name, @Email String email, @NotBlank String password) {}
    public record LoginRequest(@Email String email, @NotBlank String password) {}
}
