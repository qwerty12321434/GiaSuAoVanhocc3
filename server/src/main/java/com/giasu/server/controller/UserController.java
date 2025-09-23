package com.giasu.server.controller;

import com.giasu.server.model.User;
import com.giasu.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> users = userRepository.findAll();
            List<Map<String, Object>> userList = users.stream()
                .map(this::convertUserToMap)
                .collect(Collectors.toList());
            return ResponseEntity.ok(userList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(createErrorResponse("SERVER_ERROR"));
        }
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody Map<String, Object> request) {
        try {
            String email = (String) request.get("email");
            if (email == null) {
                return ResponseEntity.badRequest()
                    .body(createErrorResponse("EMAIL_REQUIRED"));
            }

            String fullName = (String) request.get("full_name");
            String className = (String) request.get("class");

            User user = new User();
            user.setFullName(fullName);
            user.setEmail(email);
            user.setClassName(className);
            
            user = userRepository.save(user);
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(convertUserToMap(user));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(createErrorResponse("SERVER_ERROR"));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        try {
            Optional<User> userOpt = userRepository.findById(id);
            if (userOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(createErrorResponse("USER_NOT_FOUND"));
            }

            User user = userOpt.get();
            String fullName = (String) request.get("full_name");
            String email = (String) request.get("email");
            String className = (String) request.get("class");

            if (fullName != null) user.setFullName(fullName);
            if (email != null) user.setEmail(email);
            if (className != null) user.setClassName(className);

            user = userRepository.save(user);
            return ResponseEntity.ok(convertUserToMap(user));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(createErrorResponse("SERVER_ERROR"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        try {
            Optional<User> userOpt = userRepository.findById(id);
            if (userOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(createErrorResponse("USER_NOT_FOUND"));
            }

            return ResponseEntity.ok(convertUserToMap(userOpt.get()));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(createErrorResponse("SERVER_ERROR"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            if (!userRepository.existsById(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(createErrorResponse("USER_NOT_FOUND"));
            }

            userRepository.deleteById(id);
            return ResponseEntity.noContent().build();

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(createErrorResponse("SERVER_ERROR"));
        }
    }

    private Map<String, Object> convertUserToMap(User user) {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("id", user.getId());
        userMap.put("full_name", user.getFullName());
        userMap.put("name", user.getFullName()); // For compatibility
        userMap.put("email", user.getEmail());
        userMap.put("role", user.getRole());
        userMap.put("created_at", user.getCreatedAt());
        return userMap;
    }

    private Map<String, String> createErrorResponse(String error) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", error);
        return errorResponse;
    }
}

