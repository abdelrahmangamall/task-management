package com.task.task_management.controller;


import com.task.task_management.dto.AuthResponse;
import com.task.task_management.dto.LoginRequest;
import com.task.task_management.dto.RegisterRequest;
import com.task.task_management.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        try {
            AuthResponse response = authService.register(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
//        try {
//            AuthResponse response = authService.login(request);
//            return ResponseEntity.ok(response);
//        } catch (RuntimeException e) {
//            Map<String, String> error = new HashMap<>();error.put("error", "Invalid email or password");
//            System.out.println(e);
//            return ResponseEntity.badRequest().body(error);
//
//        }
        return ResponseEntity.ok(authService.login(request));

    }
}