package com.oswaldo.users.service;

import com.oswaldo.users.controller.AuthController;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.Map;

public interface UserService {

    Map<String, Object> me(Authentication auth);


    ResponseEntity<?> registerUser(AuthController.RegisterRequest req);

    ResponseEntity<?> login(AuthController.LoginRequest req);
}
