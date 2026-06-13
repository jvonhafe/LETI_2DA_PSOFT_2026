package com.aisafe.controller;

import com.aisafe.core.security.JwtService;
import com.aisafe.model.User;
import com.aisafe.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Autenticação JWT")
public class AuthController {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public AuthController(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Operation(summary = "Login e geração de token JWT")
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        if (request == null || request.username == null || request.password == null) {
            return new LoginResponse("Invalid credentials");
        }
        Optional<User> userOptional = userRepository.findByUsername(request.username);

        if (userOptional.isEmpty() || !userOptional.get().getPassword().equals(request.password)) {
            return new LoginResponse("Invalid credentials");
        }

        User user = userOptional.get();

        String token = jwtService.generateToken(user);

        return new LoginResponse(token);
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