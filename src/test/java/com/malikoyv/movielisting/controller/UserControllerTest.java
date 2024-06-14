package com.malikoyv.movielisting.controller;


import com.malikoyv.movielisting.config.JwtService;
import com.malikoyv.movielisting.model.ERole;
import com.malikoyv.movielisting.model.Role;
import com.malikoyv.movielisting.model.User;
import com.malikoyv.movielisting.repos.UserRepository;
import com.malikoyv.movielisting.service.UserService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {
    @MockBean
    private UserService userService;
    @InjectMocks
    private UserController userController;
    private UserRepository userRepository;
    @MockBean
    private JwtService jwtService;

    private final ObjectId universalId = new ObjectId("666bfd65b0361c3256c94072");
    private final User user = new User(universalId,
            "username1",
            "email@gmail.com",
            "123456",
            Set.of(new Role(ERole.USER)));

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        MockitoAnnotations.openMocks(this);
    }

    // successful tests

    @Test
    void getAllUsers_success(){
        List<User> users = new ArrayList<>(Arrays.asList(new User("username"), new User("username1")));
        when(userService.getAllUsers()).thenReturn(users);
        ResponseEntity<List<User>> response = userController.getAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
    }

    @Test
    void getUserById_success(){
        when(userService.getUserById(universalId)).thenReturn(Optional.of(user));
        ResponseEntity<User> response = userController.getById(universalId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void createUser_success(){
        when(userService.addUser(user)).thenReturn(user);
        ResponseEntity<User> response = userController.addUser(user);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void getByUsername_success(){
        when(userService.getUserByUsername(user.getUsername())).thenReturn(Optional.of(user));
        ResponseEntity<User> response = userController.getByUsername(user.getUsername());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void updateUsername_success(){
        String value = "new_username";
        User updated = new User(user.get_id(), value);
        when(userService.updateUsername(user.get_id(), value)).thenReturn(updated);
        ResponseEntity<User> response = userController.updateUsername(user.get_id(), "new_username");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updated, response.getBody());
    }

    @Test
    void updatePassword_success(){
        String value = "new_password";
        User updated = new User(user.get_id(), value);
        when(userService.updatePassword(user.get_id(), value)).thenReturn(updated);
        ResponseEntity<User> response = userController.updatePassword(user.get_id(), value);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updated, response.getBody());
    }

    @Test
    void deleteUser_success(){
        when(userService.deleteUser(universalId)).thenReturn(true);
        ResponseEntity<HttpStatus> response = userController.deleteUser(universalId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    // failed tests

    @Test
    void getAllUsers_notFound(){
        List<User> users = new ArrayList<>();
        when(userService.getAllUsers()).thenReturn(users);
        ResponseEntity<List<User>> response = userController.getAll();
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void getUserById_notFound(){
        when(userService.getUserById(universalId)).thenReturn(Optional.empty());
        ResponseEntity<User> response = userController.getById(universalId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void getByUsername_notFound(){
        when(userService.getUserByUsername(user.getUsername())).thenReturn(Optional.empty());
        ResponseEntity<User> response = userController.getByUsername(user.getUsername());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void addUser_notUniqueUsername(){
        User newUser = new User(universalId, user.getUsername(), "newemail@gmail.com", "password456", Set.of(new Role(ERole.USER)));
        // Mock the behavior of the UserService to return the existing user when searching by username
        when(userService.getUserByUsername(newUser.getUsername())).thenReturn(Optional.of(user));
        // Mock the addUser method to return null since the username is not unique
        when(userService.addUser(newUser)).thenReturn(null);
        // Call the addUser method on the UserController
        ResponseEntity<User> response = userController.addUser(newUser);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void addUser_invalidEmail(){
        user.setEmail("example@gmail.c");
        when(userService.addUser(user)).thenReturn(null);
        ResponseEntity<User> response = userController.addUser(user);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void addUser_invalidPassword(){
        user.setPassword("123");
        when(userService.addUser(user)).thenReturn(null);
        ResponseEntity<User> response = userController.addUser(user);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void addUser_emptyUsername(){
        user.setUsername("");
        when(userService.addUser(user)).thenReturn(null);
        ResponseEntity<User> response = userController.addUser(user);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void updateUsername_emptyUsername(){
        user.setUsername("");
        when(userService.updateUsername(user.get_id(), user.getUsername())).thenReturn(null);
        ResponseEntity<User> response = userController.updateUsername(user.get_id(), user.getUsername());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }
}
