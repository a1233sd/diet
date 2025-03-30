package com.example.test.сontroller;

import com.example.test.dbase.User;
import com.example.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable("userId") Long userId) {
        if (userId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            User user = userService.getUserById(userId);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody User user) {
        String validationError = validateUser(user);
        if (validationError != null) {
            return new ResponseEntity<>(validationError, HttpStatus.BAD_REQUEST);
        }

        User createdUser = userService.createUser(user);
        return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
    }

    private String validateUser(User user) {
        if (user.getUserName() == null || user.getUserName().trim().isEmpty()) {
            return "User name is required.";
        }
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            return "Email is required.";
        }
        if (user.getGoal() == null) {
            return "Goal is required.";
        }

        // Проверка диапазонов значений
        if (user.getWeight() == null || user.getWeight() < 30 || user.getWeight() > 350) {
            return "Weight must be between 30 and 350 kg.";
        }
        if (user.getHeight() == null || user.getHeight() < 50 || user.getHeight() > 250) {
            return "Height must be between 50 and 250 cm.";
        }
        if (user.getAge() == null || user.getAge() < 18 || user.getAge() > 120) {
            return "Age must be between 18 and 120 years.";
        }
        return null;
    }

}
