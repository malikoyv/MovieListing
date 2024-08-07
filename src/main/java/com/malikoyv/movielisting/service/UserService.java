package com.malikoyv.movielisting.service;

import com.malikoyv.movielisting.model.User;
import com.malikoyv.movielisting.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

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
        if (isUserValid(user)) {
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

    public User updatePassword(ObjectId id, String password) {
        Optional<User> user = userRepository.findById(id.toString());
        if (user.isEmpty() || user.get().getPassword().equals(password) || password.length() < 6){
            return null;
        }
        user.get().setPassword(password);
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

    public boolean isUserValid(User user) {
        if (user == null || user.getUsername() == null || user.getEmail() == null || user.getPassword() == null) {
            return false;
        }
        boolean existingUser = userRepository.existsById(user.getUsername());
        return !user.getUsername().isEmpty()
                && isEmailValid(user.getEmail())
                && !existingUser
                && user.getPassword().length() > 5;
    }

    private boolean isEmailValid(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p = Pattern.compile(ePattern);
        Matcher m = p.matcher(email);
        return m.matches();
    }
}