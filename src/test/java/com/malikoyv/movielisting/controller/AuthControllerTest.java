package com.malikoyv.movielisting.controller;

import com.malikoyv.movielisting.auth.*;
import com.malikoyv.movielisting.config.JwtService;
import com.malikoyv.movielisting.model.ERole;
import com.malikoyv.movielisting.model.Role;
import com.malikoyv.movielisting.model.User;
import com.malikoyv.movielisting.repos.UserRepository;
import com.malikoyv.movielisting.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.exceptions.base.MockitoException;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.naming.AuthenticationException;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@WebMvcTest(controllers = AuthController.class)
public class AuthControllerTest {
    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserService userService;

    @MockBean
    private AuthService authService;

    @MockBean
    private AuthenticationManager authManager;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        authService = mock(AuthService.class);
        authController = new AuthController(authService);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register_test() {
        RegisterRequest request = new RegisterRequest("newuser", "email@email.com", "password");
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        when(passwordEncoder.encode(user.getPassword())).thenReturn(request.getPassword());
        when(userService.isUserValid(user)).thenReturn(true);
        when(userRepository.save(user)).thenReturn(user);
        when(jwtService.generateToken(user)).thenReturn("token");
        when(authService.register(request)).thenReturn(new AuthenticationResponse("token"));

        ResponseEntity<AuthenticationResponse> response = authController.register(request);
        assertEquals("token", response.getBody().getToken());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void authenticate_test(){
        AuthenticationRequest request = new AuthenticationRequest ("newuser", "password");
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        when(jwtService.generateToken(user)).thenReturn("token");
        when(authService.authenticate(request)).thenReturn(new AuthenticationResponse("token"));
        ResponseEntity<AuthenticationResponse> response = authController.authenticate(request);

        assertEquals("token", response.getBody().getToken());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testAuthenticateInvalidCredentials() {
        AuthenticationRequest request = new AuthenticationRequest("invaliduser", "wrongpassword");

        doThrow(BadCredentialsException.class)
                .when(authManager)
                .authenticate(any());
        when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.empty());
        ResponseEntity<AuthenticationResponse> response = authController.authenticate(request);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
}
