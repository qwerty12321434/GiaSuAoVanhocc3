package com.giasu.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HealthController {

    @Autowired
    private DataSource dataSource;

    @GetMapping("/health")
    public ResponseEntity<?> healthCheck() {
        try {
            // Test database connection
            try (Connection connection = dataSource.getConnection()) {
                Map<String, Object> response = new HashMap<>();
                response.put("ok", true);
                response.put("now", LocalDateTime.now());
                response.put("database", connection.getMetaData().getDatabaseProductName());
                response.put("database_version", connection.getMetaData().getDatabaseProductVersion());
                response.put("url", connection.getMetaData().getURL());
                response.put("username", connection.getMetaData().getUserName());
                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("ok", false);
            response.put("error", "DB_ERROR");
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

