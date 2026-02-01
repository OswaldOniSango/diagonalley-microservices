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

import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

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
        return Map.of("id", user.getId(), "name", user.getName(), "lastName", user.getLastName(), "email", user.getEmail(), "role", user.getRole());
    }

    @Override
    public ResponseEntity<?> updateRole(Long userId, String role) {
        var user = repo.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "User not found"));
        }
        String normalized = normalizeRole(role);
        user.setRole(normalized);
        repo.save(user);
        return ResponseEntity.ok(Map.of("id", user.getId(), "email", user.getEmail(), "role", user.getRole()));
    }

    @Override
    public ResponseEntity<?> listUsers() {
        var users = repo.findAll().stream()
                .map(user -> Map.of(
                        "id", user.getId(),
                        "name", user.getName(),
                        "lastName", user.getLastName(),
                        "email", user.getEmail(),
                        "role", user.getRole()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @Override
    public ResponseEntity<?> registerUser(AuthController.RegisterRequest req) {

        if (repo.existsByEmail(req.email())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "Email already registered"));
        }
        User user = new User();
        user.setName(req.name());
        user.setLastName(req.lastName());
        user.setEmail(req.email());
        user.setPasswordHash(encoder.encode(req.password()));
        user.setRole("USER");
        repo.save(user);
        String token = jwtUtil.generateToken(user.getEmail(), java.util.List.of(user.getRole()));
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("token: ", "Bearer " + token, "role", user.getRole()));
    }

    @Override
    public ResponseEntity<?> login(AuthController.LoginRequest req) {
        User user = repo.findByEmail(req.email()).orElse(null);
        ResponseEntity<?> responseEntity;
        if (user != null && encoder.matches(req.password(), user.getPasswordHash())) {
            responseEntity = ResponseEntity.ok(Map.of(
                    "token: ", "Bearer " + jwtUtil.generateToken(user.getEmail(), java.util.List.of(user.getRole()))
                    , "name", user.getName()
                    , "lastName", user.getLastName()
                    , "email", user.getEmail()
                    , "role", user.getRole()
            ));
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid email or password"));
        }
        return responseEntity;
    }

    private String normalizeRole(String role) {
        String normalized = role == null ? "" : role.trim().toUpperCase(Locale.ROOT);
        if (normalized.startsWith("ROLE_")) {
            normalized = normalized.substring("ROLE_".length());
        }
        return normalized.isBlank() ? "USER" : normalized;
    }
}
