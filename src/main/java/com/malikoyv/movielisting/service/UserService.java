package com.malikoyv.movielisting.service;

import com.malikoyv.movielisting.model.User;
import com.malikoyv.movielisting.repos.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return userRepository.findAll();
    }

    public Optional<User> getUserById(ObjectId id) {
        return userRepository.findById(id.toString());
    }

    public Optional<User> getUserByUsername(String username) {
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
        Optional<User> user = userRepository.findById(id.toString());
        if (!isUsernameValid(id, username) || user.isEmpty()){
            return null;
        }
        user.get().setUsername(username);
        return userRepository.save(user.get());
    }

    public boolean deleteUser(ObjectId id){
        if (userRepository.existsById(id.toString())) {
            userRepository.deleteById(id.toString());
            return true;
        }
        return false;
    }

    private boolean isUsernameValid(ObjectId id, String username) {
        Optional<User> existingUser = userRepository.findByUsername(username);
        // If a user with the new username already exists, and it's not the current user
        return existingUser.isEmpty() || existingUser.get().get_id().equals(id);
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