package com.example.test.service;

import com.example.test.dbase.User;
import com.example.test.repository.UserRepository;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(@NotNull User user) {
        return userRepository.save(user);
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateUser(Long userId, @NotNull User userDetails) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setUserName(userDetails.getUserName());
        user.setEmail(userDetails.getEmail());
        user.setAge(userDetails.getAge());
        user.setWeight(userDetails.getWeight());
        user.setHeight(userDetails.getHeight());
        user.setGoal(userDetails.getGoal());
        user.setDailyCalories(userDetails.getDailyCalories()); // Этот параметр можно обновлять, если нужно
        return userRepository.save(user);
    }
}