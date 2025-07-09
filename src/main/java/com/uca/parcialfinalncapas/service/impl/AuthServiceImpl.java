package com.uca.parcialfinalncapas.service.impl;

import com.uca.parcialfinalncapas.dto.request.LoginRequest;
import com.uca.parcialfinalncapas.dto.response.LoginResponse;
import com.uca.parcialfinalncapas.entities.User;
import com.uca.parcialfinalncapas.repository.UserRepository;
import com.uca.parcialfinalncapas.service.AuthService;
import com.uca.parcialfinalncapas.service.CustomUserDetailsService;
import com.uca.parcialfinalncapas.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService userDetailsService;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByCorreo(loginRequest.getCorreo())
                .orElseThrow(() -> new BadCredentialsException("Credenciales inválidas"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Credenciales inválidas");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getCorreo());
        String token = jwtUtil.generateToken(userDetails, user.getNombreRol());

        return LoginResponse.builder()
                .token(token)
                .correo(user.getCorreo())
                .nombre(user.getNombre())
                .rol(user.getNombreRol())
                .build();
    }
} 