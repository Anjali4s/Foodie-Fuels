package com.curiouscoders.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.curiouscoders.dto.UserDTO;
import com.curiouscoders.service.IUserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private IUserService userService;

    // Create a new user with manual validation
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody Map<String, Object> requestBody) {
        String name = (String) requestBody.get("name");
        String email = (String) requestBody.get("email");
        String password = (String) requestBody.get("password");
        Boolean active = (Boolean) requestBody.get("active");

        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Username cannot be empty");
        }
        if (email == null || email.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Email cannot be empty");
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            return ResponseEntity.badRequest().body("Invalid email format");
        }
        if (password == null || password.length() < 6) {
            return ResponseEntity.badRequest().body("Password must be at least 6 characters long");
        }
        if (active == null) {
            return ResponseEntity.badRequest().body("Active status must be provided");
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setName(name);
        userDTO.setEmail(email);
        userDTO.setPassword(password);
        userDTO.setActive(active);

        UserDTO createdUser = userService.createUser(userDTO);
        return ResponseEntity.ok(createdUser);
    }

    // Get user by ID
    @PostMapping("/getById")
    public ResponseEntity<?> getUserById(@RequestBody Map<String, Long> requestBody) {
        Long id = requestBody.get("id");
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().body("Invalid user ID");
        }
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // Get user by email
    @PostMapping("/getByEmail")
    public ResponseEntity<?> getUserByEmail(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        if (email == null || email.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Email cannot be empty");
        }
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    // Get all users
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // Update user details
    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody Map<String, Object> requestBody) {
        Long id = ((Number) requestBody.get("id")).longValue();
        String email = (String) requestBody.get("email");

        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().body("Invalid user ID");
        }
        if (email == null || email.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Email cannot be empty");
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(email);

        return ResponseEntity.ok(userService.updateUser(id, userDTO));
    }

    // Deactivate a user
    @PatchMapping("/deactivate")
    public ResponseEntity<?> deactivateUser(@RequestBody Map<String, Long> requestBody) {
        Long id = requestBody.get("id");
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().body("Invalid user ID");
        }
        userService.deactivateUser(id);
        return ResponseEntity.ok("User deactivated successfully.");
    }

    // Activate a user
    @PatchMapping("/activate")
    public ResponseEntity<?> activateUser(@RequestBody Map<String, Long> requestBody) {
        Long id = requestBody.get("id");
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().body("Invalid user ID");
        }
        userService.activateUser(id);
        return ResponseEntity.ok("User activated successfully.");
    }

    // Change user password
    @PatchMapping("/changePassword")
    public ResponseEntity<?> changeUserPassword(@RequestBody Map<String, Object> requestBody) {
        Long id = ((Number) requestBody.get("id")).longValue();
        String newPassword = (String) requestBody.get("newPassword");

        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().body("Invalid user ID");
        }
        if (newPassword == null || newPassword.length() < 6) {
            return ResponseEntity.badRequest().body("Password must be at least 6 characters long");
        }

        userService.changeUserPassword(id, newPassword);
        return ResponseEntity.ok("Password changed successfully.");
    }
}