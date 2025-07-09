package com.uca.parcialfinalncapas.utils;

import com.uca.parcialfinalncapas.entities.User;
import com.uca.parcialfinalncapas.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityUtils {

    private final UserRepository userRepository;

    /**
     * Obtiene el email del usuario autenticado
     */
    public String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            return ((UserDetails) authentication.getPrincipal()).getUsername();
        }
        return null;
    }

    /**
     * Obtiene el usuario completo autenticado
     */
    public User getCurrentUser() {
        String email = getCurrentUserEmail();
        if (email != null) {
            return userRepository.findByCorreo(email).orElse(null);
        }
        return null;
    }

    /**
     * Verifica si el usuario actual tiene un rol específico
     */
    public boolean hasRole(String role) {
        User user = getCurrentUser();
        return user != null && role.equals(user.getNombreRol());
    }

    /**
     * Verifica si el usuario actual es un técnico
     */
    public boolean isTech() {
        return hasRole("TECH");
    }

    /**
     * Verifica si el usuario actual es un usuario normal
     */
    public boolean isUser() {
        return hasRole("USER");
    }
} 