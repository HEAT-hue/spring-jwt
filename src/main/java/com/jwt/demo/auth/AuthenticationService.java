package com.jwt.demo.auth;

import com.jwt.demo.config.JwtUtil;
import com.jwt.demo.entity.Role;
import com.jwt.demo.entity.User;
import com.jwt.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    // Save User in the DB
    public AuthResponseDTO register(RegisterRequestDTO registerRequestDTO) {
        // Build user
        User user = new User();
        user.setFirstName(registerRequestDTO.getFirstName());
        user.setLastName(registerRequestDTO.getLastName());
        user.setEmail(registerRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        user.setRole(Role.ADMIN);
        try {
            // TODO: Check if user has been created
            userRepository.save(user);
        } catch (Exception exception) {
            // TODO: Create your exception class
            throw new RuntimeException(exception.getMessage());
        }
        String jwtToken = jwtUtil.generateToken(user);
        return new AuthResponseDTO(jwtToken);
    }

    public AuthResponseDTO authenticate(AuthRequestDTO authRequestDTO) {

        // Authenticate user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequestDTO.getEmail(),
                        authRequestDTO.getPassword())
        );

        // Fetch user
        User user = userRepository.findByEmail(authRequestDTO.getEmail()).orElseThrow();

        // Generate token
        String jwtToken = jwtUtil.generateToken(user);

        // Return token
        return new AuthResponseDTO(jwtToken);
    }
}
