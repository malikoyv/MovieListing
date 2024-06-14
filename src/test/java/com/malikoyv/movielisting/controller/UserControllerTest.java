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
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.lang.reflect.Array;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    @MockBean
    private UserService userService;

    @MockBean
    private JwtService jwtService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User(new ObjectId(), "user1"));
        when(userService.getAllUsers()).thenReturn(users);

        ResponseEntity<List<User>> response = userController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
    }

    @Test
    void testGetAllUsersEmpty() {
        when(userService.getAllUsers()).thenReturn(new ArrayList<>());

        ResponseEntity<List<User>> response = userController.getAll();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetUserById() {
        ObjectId userId = new ObjectId();
        User user = new User(userId, "user1");
        when(userService.getUserById(userId)).thenReturn(Optional.of(user));

        ResponseEntity<User> response = userController.getById(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void testGetUserByIdNotFound() {
        ObjectId userId = new ObjectId();
        when(userService.getUserById(userId)).thenReturn(Optional.empty());

        ResponseEntity<User> response = userController.getById(userId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetUserByUsername() {
        User user = new User(new ObjectId(), "user1");
        when(userService.getUserByUsername("user1")).thenReturn(Optional.of(user));

        ResponseEntity<User> response = userController.getByUsername("user1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void testGetUserByUsernameNotFound() {
        when(userService.getUserByUsername("user1")).thenReturn(Optional.empty());

        ResponseEntity<User> response = userController.getByUsername("user1");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testAddUser() {
        User user = new User(new ObjectId(), "user1");
        when(userService.addUser(user)).thenReturn(user);

        ResponseEntity<User> response = userController.addUser(user);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void testAddUserInvalid() {
        User user = new User(new ObjectId(), "user1");
        when(userService.addUser(user)).thenReturn(null);

        ResponseEntity<User> response = userController.addUser(user);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testUpdateUsername() {
        ObjectId userId = new ObjectId();
        User user = new User(userId, "newUsername");
        when(userService.updateUsername(userId, "newUsername")).thenReturn(user);

        ResponseEntity<User> response = userController.updateUsername(userId, "newUsername");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void testUpdateUsernameInvalid() {
        ObjectId userId = new ObjectId();
        when(userService.updateUsername(userId, "newUsername")).thenReturn(null);

        ResponseEntity<User> response = userController.updateUsername(userId, "newUsername");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testUpdatePassword() {
        ObjectId userId = new ObjectId();
        User user = new User(userId, "user1", "user1@example.com", "newPassword", Set.of(new Role(ERole.USER)));
        when(userService.updatePassword(userId, "newPassword")).thenReturn(user);

        ResponseEntity<User> response = userController.updatePassword(userId, "newPassword");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void testUpdatePasswordInvalid() {
        ObjectId userId = new ObjectId();
        when(userService.updatePassword(userId, "newPassword")).thenReturn(null);

        ResponseEntity<User> response = userController.updatePassword(userId, "newPassword");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testDeleteUser() {
        ObjectId userId = new ObjectId();
        when(userService.deleteUser(userId)).thenReturn(true);

        ResponseEntity<HttpStatus> response = userController.deleteUser(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testDeleteUserNotFound() {
        ObjectId userId = new ObjectId();
        when(userService.deleteUser(userId)).thenReturn(false);

        ResponseEntity<HttpStatus> response = userController.deleteUser(userId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}