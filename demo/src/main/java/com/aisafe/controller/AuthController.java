package com.aisafe.controller;

import com.aisafe.core.security.JwtService;
import com.aisafe.model.User;
import com.aisafe.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        System.out.println("DEBUG: Tentativa de login para username: " + request.username);

        Optional<User> userOptional = userRepository.findByUsername(request.username);

        if (userOptional.isEmpty()) {
            System.out.println("DEBUG: Utilizador não encontrado!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username não existe.");
        }

        User user = userOptional.get();
        System.out.println("DEBUG: Password esperada: " + user.getPassword());
        System.out.println("DEBUG: Password recebida: " + request.password);

        if (!user.getPassword().equals(request.password)) {
            System.out.println("DEBUG: Password incorreta!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Password errada.");
        }

        String token = jwtService.generateToken(user);
        return ResponseEntity.ok(new LoginResponse(token));
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