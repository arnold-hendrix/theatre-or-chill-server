// manages access and updates to User persistence.

package com.arnold.mas.theaterorchill.service;

import com.arnold.mas.theaterorchill.exceptions.UserNotFoundException;
import com.arnold.mas.theaterorchill.model.User;
import com.arnold.mas.theaterorchill.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    // constructor autowiring for userRepository object.

    public User addUser(User user) {
            return userRepository.save(user);
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User by id " + id + "was not found"));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUserInformation(User user) {
        return userRepository.save(user);
    }

    public void deleteUserAccount(Long id) {
        userRepository.deleteUserById(id);
    }
}
