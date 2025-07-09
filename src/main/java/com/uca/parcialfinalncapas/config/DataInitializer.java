package com.uca.parcialfinalncapas.config;

import com.uca.parcialfinalncapas.entities.User;
import com.uca.parcialfinalncapas.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Eliminar usuarios existentes para recrearlos con contraseñas encriptadas
        userRepository.deleteAll();
        
        // Usuario normal
        User user = User.builder()
                .nombre("Usuario Normal")
                .correo("user@test.com")
                .password(passwordEncoder.encode("password123"))
                .nombreRol("USER")
                .build();
        userRepository.save(user);

        // Técnico
        User tech = User.builder()
                .nombre("Técnico")
                .correo("tech@test.com")
                .password(passwordEncoder.encode("password123"))
                .nombreRol("TECH")
                .build();
        userRepository.save(tech);

        System.out.println("Usuarios de prueba recreados con contraseñas encriptadas:");
        System.out.println("USER: user@test.com / password123");
        System.out.println("TECH: tech@test.com / password123");
    }
} 