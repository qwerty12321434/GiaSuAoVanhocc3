package com.giasu.server.controller;

import com.giasu.server.model.Author;
import com.giasu.server.repository.AuthorRepository;
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
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping
    public ResponseEntity<?> getAllAuthors() {
        try {
            List<Author> authors = authorRepository.findAll();
            List<Map<String, Object>> authorList = authors.stream()
                .map(this::convertAuthorToMap)
                .collect(Collectors.toList());
            return ResponseEntity.ok(authorList);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(createErrorResponse("SERVER_ERROR"));
        }
    }

    @PostMapping
    public ResponseEntity<?> createAuthor(@RequestBody Map<String, Object> request) {
        try {
            String name = (String) request.get("name");
            if (name == null || name.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(createErrorResponse("NAME_REQUIRED"));
            }

            String bio = (String) request.get("bio");

            // Generate custom ID: highest existing ID + 1, or 1 if empty
            Long nextId = getNextAuthorId();
            System.out.println("DEBUG: Creating author with ID: " + nextId);
            
            // Create author with custom ID
            Author author = new Author(nextId, name.trim(), bio);
            System.out.println("DEBUG: Author object created with ID: " + author.getId());
            author = authorRepository.save(author);
            System.out.println("DEBUG: Author saved with ID: " + author.getId());
            
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(convertAuthorToMap(author));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(createErrorResponse("SERVER_ERROR"));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAuthor(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        try {
            Optional<Author> authorOpt = authorRepository.findById(id);
            if (authorOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(createErrorResponse("AUTHOR_NOT_FOUND"));
            }

            Author author = authorOpt.get();
            String name = (String) request.get("name");
            String bio = (String) request.get("bio");

            if (name != null && !name.trim().isEmpty()) {
                author.setName(name.trim());
            }
            if (bio != null) {
                author.setBiography(bio);
            }

            author = authorRepository.save(author);
            return ResponseEntity.ok(convertAuthorToMap(author));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(createErrorResponse("SERVER_ERROR"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAuthor(@PathVariable Long id) {
        try {
            if (!authorRepository.existsById(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(createErrorResponse("AUTHOR_NOT_FOUND"));
            }

            authorRepository.deleteById(id);
            return ResponseEntity.noContent().build();

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(createErrorResponse("SERVER_ERROR"));
        }
    }

    private Long getNextAuthorId() {
        try {
            Long maxId = authorRepository.findMaxId();
            System.out.println("DEBUG: Max ID found: " + maxId);
            Long nextId = (maxId != null) ? maxId + 1 : 1L;
            System.out.println("DEBUG: Next ID will be: " + nextId);
            return nextId;
        } catch (Exception e) {
            System.out.println("DEBUG: Error getting max ID: " + e.getMessage());
            // If there's an error getting max ID, start from 1
            return 1L;
        }
    }

    private Map<String, Object> convertAuthorToMap(Author author) {
        Map<String, Object> authorMap = new HashMap<>();
        authorMap.put("id", author.getId());
        authorMap.put("name", author.getName());
        authorMap.put("bio", author.getBiography());
        authorMap.put("created_at", author.getCreatedAt());
        authorMap.put("updated_at", author.getUpdatedAt());
        return authorMap;
    }

    private Map<String, String> createErrorResponse(String error) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", error);
        return errorResponse;
    }
}