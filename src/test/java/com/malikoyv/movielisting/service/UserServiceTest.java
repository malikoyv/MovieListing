package com.malikoyv.movielisting.service;

import com.malikoyv.movielisting.model.User;
import com.malikoyv.movielisting.repos.UserRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;
    private final ObjectId userId = new ObjectId();
    private final List<User> users = new ArrayList<>();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.set_id(userId);
        user.setUsername("username");
        user.setPassword("password");
        user.setEmail("email@email.com");

        users.add(user);
    }

    @Test
    void getAllUsers_success(){
        when(userRepository.findAll()).thenReturn(users);
        List<User> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(users, result);
    }

    @Test
    void getAllUsers_failed(){
        when(userRepository.findAll()).thenReturn(new ArrayList<>());
        List<User> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(new ArrayList<>(), result);
    }

    @Test
    void getUserById_success(){
        when(userRepository.findById(userId.toString())).thenReturn(Optional.of(user));
        Optional<User> result = userService.getUserById(userId);

        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void getUserById_failed(){
        when(userRepository.findById(userId.toString())).thenReturn(Optional.empty());
        Optional<User> result = userService.getUserById(userId);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getUserByUsername_success(){
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        Optional<User> result = userService.getUserByUsername(user.getUsername());

        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
        assertEquals(user.getUsername(), result.get().getUsername());
        assertEquals(user.getUsername(), "username");
    }

    @Test
    void getUserByUsername_failed(){
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        Optional<User> result = userService.getUserByUsername(user.getUsername());

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void addUser_success(){
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        when(userRepository.existsById(user.getUsername())).thenReturn(false);
        when(userRepository.save(user)).thenReturn(user);
        User result = userService.addUser(user);

        assertNotNull(result);
        assertEquals(user, result);
    }

    @Test
    void addUser_failed(){
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        User result = userService.addUser(user);

        assertNull(result);
    }

    @Test
    void updateUsername_success(){
        String newUsername = "newUsername";
        when(userRepository.findById(userId.toString())).thenReturn(Optional.of(user));
        when(userRepository.findByUsername(newUsername)).thenReturn(Optional.empty());
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.updateUsername(userId, newUsername);

        assertNotNull(result);
        assertEquals(newUsername, result.getUsername());
    }

    @Test
    void updateUsername_failed(){
        String newUsername = "newUsername";
        when(userRepository.findById(userId.toString())).thenReturn(Optional.of(user));
        when(userRepository.findByUsername(newUsername)).thenReturn(Optional.of(user));

        User result = userService.updateUsername(userId, newUsername);

        assertNull(result);
    }

    @Test
    void updatePassword_success(){
        String newPassword = "newPassword";
        when(userRepository.findById(userId.toString())).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.updatePassword(userId, newPassword);

        assertNotNull(result);
        assertEquals(newPassword, result.getPassword());
    }

    @Test
    void updatePassword_failed_repeated(){
        when(userRepository.findById(userId.toString())).thenReturn(Optional.of(user));

        User result = userService.updatePassword(userId, user.getPassword());

        assertNull(result);
    }

    @Test
    void updatePassword_failed_short(){
        String newPassword = "123";
        when(userRepository.findById(userId.toString())).thenReturn(Optional.of(user));

        User result = userService.updatePassword(userId, newPassword);

        assertNull(result);
    }

    @Test
    void updatePassword_failed_empty(){
        String newPassword = null;
        when(userRepository.findById(userId.toString())).thenReturn(Optional.empty());
        User result = userService.updatePassword(userId, newPassword);

        assertNull(result);
    }

    @Test
    void deleteUser_success(){
        when(userRepository.existsById(userId.toString())).thenReturn(true);
        boolean result = userService.deleteUser(userId);

        assertTrue(result);
    }

    @Test
    void deleteUser_failed(){
        when(userRepository.existsById(userId.toString())).thenReturn(false);
        boolean result = userService.deleteUser(userId);

        assertFalse(result);
    }
}
