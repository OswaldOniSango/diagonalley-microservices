package com.oswaldo.users.service;

import com.oswaldo.common.security.JwtUtil;
import com.oswaldo.users.controller.AuthController;
import com.oswaldo.users.model.User;
import com.oswaldo.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserServiceImpl implements UserService{

    UserRepository repo;
    PasswordEncoder encoder;
    JwtUtil jwtUtil;

    public UserServiceImpl(UserRepository repo, PasswordEncoder encoder, JwtUtil jwtUtil) {
        this.repo = repo;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
    }


    @Override
    public Map<String, Object> me(Authentication auth) {
        String email = auth.getName();
        var user = repo.findByEmail(email).orElseThrow();
        return Map.of("id", user.getId(), "name", user.getName(), "email", user.getEmail());
    }

    @Override
    public ResponseEntity<?> registerUser(AuthController.RegisterRequest req) {

        if (repo.existsByEmail(req.email())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "Email already registered"));
        }
        User user = new User();
        user.setName(req.name());
        user.setEmail(req.email());
        user.setPasswordHash(encoder.encode(req.password()));
        repo.save(user);
        String token = jwtUtil.generateToken(user.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("token: ", "Bearer " + token));
    }

    @Override
    public ResponseEntity<?> login(AuthController.LoginRequest req) {
        return repo.findByEmail(req.email())
                .filter(u -> encoder.matches(req.password(), u.getPasswordHash()))
                .map(u -> ResponseEntity.ok(Map.of("token: ", "Bearer " + jwtUtil.generateToken(u.getEmail()))))
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error: ", "Invalid credentials")));
    }
}
