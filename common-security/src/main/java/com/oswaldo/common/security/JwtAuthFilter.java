package com.oswaldo.common.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    public JwtAuthFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String auth = request.getHeader("Authorization");
        if (auth != null && auth.startsWith("Bearer ")) {
            String token = auth.substring(7);
            try {
                Claims claims = jwtUtil.parseToken(token);
                String subject = claims.getSubject();
                List<SimpleGrantedAuthority> authorities = resolveAuthorities(claims);
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(subject, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } catch (Exception ignored) {
                // Invalid token -> no auth in context
            }
        }
        filterChain.doFilter(request, response);
    }

    private List<SimpleGrantedAuthority> resolveAuthorities(Claims claims) {
        Object rolesClaim = claims.get("roles");
        List<String> roles = new ArrayList<>();
        if (rolesClaim instanceof List<?> list) {
            for (Object item : list) {
                if (item != null) {
                    String role = item.toString().trim();
                    if (!role.isBlank()) {
                        roles.add(role);
                    }
                }
            }
        } else if (rolesClaim instanceof String roleString) {
            for (String role : roleString.split(",")) {
                String trimmed = role.trim();
                if (!trimmed.isBlank()) {
                    roles.add(trimmed);
                }
            }
        }

        if (roles.isEmpty()) {
            roles.add("USER");
        }

        List<SimpleGrantedAuthority> authorities = new ArrayList<>(roles.size());
        for (String role : roles) {
            String normalized = role.toUpperCase(Locale.ROOT);
            if (!normalized.startsWith("ROLE_")) {
                normalized = "ROLE_" + normalized;
            }
            authorities.add(new SimpleGrantedAuthority(normalized));
        }
        return authorities;
    }
}
