package com.oswaldo.users.controller;

import com.oswaldo.users.repository.UserRepository;
import com.oswaldo.users.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public ResponseEntity<?> listUsers() {
        return userService.listUsers();
    }

    @PatchMapping("/{id}/role")
    public ResponseEntity<?> updateRole(@PathVariable("id") Long id, @Valid @RequestBody UpdateRoleRequest req) {
        return userService.updateRole(id, req.role());
    }

    public record UpdateRoleRequest(@NotBlank String role) {}
}
