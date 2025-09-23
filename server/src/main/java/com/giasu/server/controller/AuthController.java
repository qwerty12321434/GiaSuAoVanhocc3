package com.giasu.server.controller;

import com.giasu.server.dto.*;
import com.giasu.server.model.User;
import com.giasu.server.repository.UserRepository;
import com.giasu.server.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
        try {
            System.out.println("Signup request received - fullName: " + request.getFullName() + ", email: " + request.getEmail());
            System.out.println("Raw request - fullName field: " + request.fullName + ", name field: " + request.name);
            
            // Check if we have a name (either fullName or name field)
            String actualName = request.getFullName();
            if (actualName == null || actualName.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(createErrorResponse("FULL_NAME_EMAIL_PASSWORD_REQUIRED"));
            }
            
            if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(createErrorResponse("FULL_NAME_EMAIL_PASSWORD_REQUIRED"));
            }
            
            if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(createErrorResponse("FULL_NAME_EMAIL_PASSWORD_REQUIRED"));
            }

            if (userRepository.existsByEmail(request.getEmail())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(createErrorResponse("EMAIL_EXISTS"));
            }

            String hashedPassword = passwordEncoder.encode(request.getPassword());
            User user = new User(actualName, request.getEmail(), hashedPassword);
            user.setClassName(request.getClassName()); // Set the class field
            user = userRepository.save(user);

            String token = jwtUtil.generateToken(user.getId());
            AuthResponse.UserResponse userResponse = new AuthResponse.UserResponse(user);
            
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AuthResponse(token, userResponse));

        } catch (Exception e) {
            System.err.println("Signup error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(createErrorResponse("SERVER_ERROR"));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            System.out.println("Login request received: " + request.getEmail());
            
            if (request.getEmail() == null || request.getPassword() == null) {
                return ResponseEntity.badRequest()
                    .body(createErrorResponse("EMAIL_PASSWORD_REQUIRED"));
            }

            Optional<User> userOpt = userRepository.findByEmail(request.getEmail());
            if (userOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(createErrorResponse("INVALID_CREDENTIALS"));
            }

            User user = userOpt.get();
            if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(createErrorResponse("INVALID_CREDENTIALS"));
            }

            String token = jwtUtil.generateToken(user.getId());
            AuthResponse.UserResponse userResponse = new AuthResponse.UserResponse(user);
            
            return ResponseEntity.ok(new AuthResponse(token, userResponse));

        } catch (Exception e) {
            System.err.println("Login error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(createErrorResponse("SERVER_ERROR"));
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(createErrorResponse("NO_TOKEN"));
            }

            String token = authHeader.substring(7);
            if (!jwtUtil.validateToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(createErrorResponse("INVALID_TOKEN"));
            }

            Long userId = jwtUtil.getUserIdFromToken(token);
            Optional<User> userOpt = userRepository.findById(userId);
            if (userOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(createErrorResponse("USER_NOT_FOUND"));
            }

            User user = userOpt.get();
            AuthResponse.UserResponse userResponse = new AuthResponse.UserResponse(user);
            
            Map<String, Object> response = new HashMap<>();
            response.put("user", userResponse);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(createErrorResponse("SERVER_ERROR"));
        }
    }

    private Map<String, String> createErrorResponse(String error) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", error);
        return errorResponse;
    }
}