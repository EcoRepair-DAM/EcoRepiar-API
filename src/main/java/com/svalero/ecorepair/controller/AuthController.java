package com.svalero.ecorepair.controller;

import com.svalero.ecorepair.domain.AppUser;
import com.svalero.ecorepair.dto.AuthResponse;
import com.svalero.ecorepair.dto.LoginRequest;
import com.svalero.ecorepair.dto.RegisterRequest;
import com.svalero.ecorepair.dto.UserResponse;
import com.svalero.ecorepair.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequest request) {
        return new ResponseEntity<>(authService.register(request), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> me(@AuthenticationPrincipal AppUser user) {
        return ResponseEntity.ok(authService.me(user));
    }
}
