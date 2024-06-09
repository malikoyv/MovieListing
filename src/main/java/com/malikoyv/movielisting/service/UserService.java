package com.malikoyv.movielisting.service;

import com.malikoyv.movielisting.model.User;
import com.malikoyv.movielisting.repos.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
    import org.springframework.web.bind.annotation.PathVariable;

import java.beans.Encoder;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        if (!users.isEmpty()) {
            return users;
        }
        return null;
    }

    public Optional<User> getUserById(ObjectId id) {
        if (userRepository.findById(id.toString()).isEmpty()) {
            return Optional.empty();
        }
        return userRepository.findById(id.toString());
    }

    public Optional<User> getUserByUsername(String username) {
        if (userRepository.findByUsername(username).isEmpty()) {
            return Optional.empty();
        }
        return userRepository.findByUsername(username);
    }

    public User addUser(User user) {
        if (isUserValid(user) && isUsernameValid(user.get_id(), user.getUsername())) {
            user.setPassword(hashPassword(user.getPassword()));
            return userRepository.save(user);
        }
        return null;
    }

    public User updateUsername(ObjectId id, String username) {
        if (!isUsernameValid(id, username)){
            return null;
        }
        Optional<User> user = userRepository.findById(id.toString());
        if (user.isEmpty()){
            return null;
        }

        return userRepository.save(user.get());
    }

    public ResponseEntity<User> deleteUser(ObjectId id){
        if (userRepository.findById(id.toString()).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userRepository.deleteById(id.toString());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private boolean isUsernameValid(ObjectId id, String username) {
        Optional<User> existingUser = userRepository.findByUsername(username);
        if (existingUser.isPresent() && !existingUser.get().get_id().equals(id)) {
            // If a user with the new username already exists and it's not the current user
            return false;
        }
        return true;
    }

    private boolean isUserValid(User user) {
        return !user.getUsername().isEmpty() && isEmailValid(user.getEmail());
    }

    private boolean isEmailValid(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p = Pattern.compile(ePattern);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    private String hashPassword(String password) {
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(password.getBytes());
    }

    private String decodePassword(String password) {
        Base64.Decoder decoder = Base64.getDecoder();
        return decoder.decode(password).toString();
    }
}