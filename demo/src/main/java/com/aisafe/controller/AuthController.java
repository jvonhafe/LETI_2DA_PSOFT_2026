package com.aisafe.controller;

import com.aisafe.core.security.JwtService;
import com.aisafe.model.User;
import com.aisafe.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
<<<<<<< HEAD
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity; // NOVO IMPORT
=======
>>>>>>> 987052eedb031394fba250f4e0e571285ef997aa
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
<<<<<<< HEAD
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        if (request == null || request.username == null || request.password == null) {
            // Devolve ERRO 401 em vez de 200 OK com texto falso
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas: Faltam dados.");
        }

        Optional<User> userOptional = userRepository.findByUsername(request.username);

        if (userOptional.isEmpty() || !userOptional.get().getPassword().equals(request.password)) {
            // Devolve ERRO 401 em vez de 200 OK
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas: Username ou password errados.");
=======
    public LoginResponse login(@RequestBody LoginRequest request) {

        if (request == null || request.username == null || request.password == null) {
            return new LoginResponse("Invalid credentials");
        }
        Optional<User> userOptional = userRepository.findByUsername(request.username);

        if (userOptional.isEmpty() || !userOptional.get().getPassword().equals(request.password)) {
            return new LoginResponse("Invalid credentials");
>>>>>>> 987052eedb031394fba250f4e0e571285ef997aa
        }

        User user = userOptional.get();

        String token = jwtService.generateToken(user);

<<<<<<< HEAD
        // Se chegou aqui, o login foi um sucesso! Devolve 200 OK com o token verdadeiro
        return ResponseEntity.ok(new LoginResponse(token));
=======
        return new LoginResponse(token);
>>>>>>> 987052eedb031394fba250f4e0e571285ef997aa
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