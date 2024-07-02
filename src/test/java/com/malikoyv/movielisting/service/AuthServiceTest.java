package com.malikoyv.movielisting.service;

import com.malikoyv.movielisting.auth.AuthService;
import com.malikoyv.movielisting.auth.AuthenticationResponse;
import com.malikoyv.movielisting.auth.RegisterRequest;
import com.malikoyv.movielisting.config.JwtService;
import com.malikoyv.movielisting.model.User;
import com.malikoyv.movielisting.repos.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthService authService;

    private RegisterRequest registerRequest;

    @BeforeEach
    void setUp() {
        registerRequest = new RegisterRequest();
        registerRequest.setUsername("testUser");
        registerRequest.setEmail("test@example.com");
        registerRequest.setPassword("password123");
    }

    @Test
    void register_success() {
        when(userService.isUserValid(any(User.class))).thenReturn(true);
        when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn("encodedPassword");
        when(jwtService.generateToken(any(User.class))).thenReturn("token");
        when(userRepository.save(any(User.class))).thenReturn(any(User.class));

        AuthenticationResponse result = authService.register(registerRequest);

        assertNotNull(result);
        assertEquals("token", result.getToken());

        verify(userService).isUserValid(any(User.class));
        verify(userRepository).save(any(User.class));
        verify(jwtService).generateToken(any(User.class));
        verify(passwordEncoder).encode(registerRequest.getPassword());
    }

    @Test
    void register_failed(){
        when(userService.isUserValid(any(User.class))).thenReturn(false);

        AuthenticationResponse result = authService.register(registerRequest);

        assertNotNull(result);
        assertEquals("invalid_data", result.getToken());

        verify(userService).isUserValid(any(User.class));
        verify(userRepository, never()).save(any(User.class));
        verify(jwtService, never()).generateToken(any(User.class));
    }
}
