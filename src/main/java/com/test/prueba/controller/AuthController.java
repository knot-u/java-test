package com.test.prueba.controller;

import com.test.prueba.model.dto.LoginRequest;
import com.test.prueba.model.dto.LoginResponse;
import com.test.prueba.security.JwtService;
import com.test.prueba.util.ApiException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;

    @Value("${app.security.admin-username}")
    private String adminUsername;

    @Value("${app.security.admin-password}")
    private String adminPassword;

    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        if (!adminUsername.equals(request.username()) || !adminPassword.equals(request.password())) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }

        String token = jwtService.generateToken(request.username());
        return new LoginResponse(token, "Bearer");
    }
}
