package com.malikoyv.movielisting.auth;

import com.malikoyv.movielisting.config.JwtService;
import com.malikoyv.movielisting.model.ERole;
import com.malikoyv.movielisting.model.Role;
import com.malikoyv.movielisting.model.User;
import com.malikoyv.movielisting.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Set.of(new Role(ERole.USER)))
                .build();
        userRepository.save(user);
        String token = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(token).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(token).build();
    }
}
