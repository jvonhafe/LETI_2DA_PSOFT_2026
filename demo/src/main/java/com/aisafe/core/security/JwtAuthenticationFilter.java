package com.aisafe.core.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Vai buscar o cabeçalho "Authorization" do Postman
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;
        final String role;

        // Se não tiver Token, ou não começar por "Bearer ", avança (pode ser uma rota livre)
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2. Extrai o Token (tira a palavra "Bearer ")
        jwt = authHeader.substring(7);

        try {
            // 3. Extrai os dados do Token usando o teu serviço
            username = jwtService.extractUsername(jwt);
            role = jwtService.extractRole(jwt);

            // 4. Se o token for válido e o utilizador ainda não estiver autenticado neste pedido
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                if (jwtService.isTokenValid(jwt)) {
                    // 5. Diz ao Spring Security: "Este utilizador está autenticado e tem esta Role!"
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            List.of(new SimpleGrantedAuthority(role))
                    );

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception e) {
            // Se o token for inválido (expirou, foi alterado), ignora e o Spring dá 403 Forbidden
        }

        // Continua a viagem até ao Controller
        filterChain.doFilter(request, response);
    }
}