package com.aisafe.controller;

import com.aisafe.core.security.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Autenticação JWT")
public class AuthController {

    private final JwtService jwtService;

    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Operation(summary = "Login e geração de token JWT")
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        if (request == null || request.username == null || request.password == null) {
            return new LoginResponse("Invalid credentials");
        }

        if (!isValidUser(request.username, request.password)) {
            return new LoginResponse("Invalid credentials");
        }

        String token = jwtService.generateToken(request.username);
        return new LoginResponse(token);
    }

    private boolean isValidUser(String username, String password) {
        return (username.equals("admin") && password.equals("admin123"))
                || (username.equals("backoffice") && password.equals("backoffice123"))
                || (username.equals("atcc") && password.equals("atcc123"))
                || (username.equals("tech") && password.equals("tech123"))
                || (username.equals("supervisor") && password.equals("supervisor123"));
    }

    public static class LoginRequest {
        public String username;
        public String password;
    }

    public static class LoginResponse {
        public String token;

        public LoginResponse(String token) {
            this.token = token;
        }
    }
}