package com.giasu.server.controller;

import com.giasu.server.model.Work;
import com.giasu.server.repository.WorkRepository;
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
@RequestMapping("/works")
public class WorkController {

    @Autowired
    private WorkRepository workRepository;

    @GetMapping
    public ResponseEntity<?> getAllWorks(@RequestParam(required = false) String grade) {
        try {
            List<Work> works;
            if (grade != null) {
                works = workRepository.findByGrade(grade);
            } else {
                works = workRepository.findAll();
            }

            List<Map<String, Object>> workList = works.stream()
                .map(this::convertWorkToMap)
                .collect(Collectors.toList());
            return ResponseEntity.ok(workList);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(createErrorResponse("SERVER_ERROR"));
        }
    }

    @PostMapping
    public ResponseEntity<?> createWork(@RequestBody Map<String, Object> request) {
        try {
            String title = (String) request.get("title");
            if (title == null || title.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(createErrorResponse("TITLE_REQUIRED"));
            }

            String grade = (String) request.get("grade");
            String summary = (String) request.get("summary");
            String analysis = (String) request.get("analysis");
            Long authorId = null;
            
            if (request.get("author_id") != null) {
                try {
                    authorId = Long.valueOf(request.get("author_id").toString());
                } catch (Exception e) {
                    return ResponseEntity.badRequest()
                        .body(createErrorResponse("INVALID_AUTHOR_ID"));
                }
            }

            // Generate custom ID: highest existing ID + 1, or 1 if empty
            Long nextId = getNextWorkId();
            System.out.println("DEBUG: Creating work with ID: " + nextId + ", authorId: " + authorId);
            
            // Create work with custom ID
            Work work = new Work(nextId, title.trim(), grade, summary, analysis, authorId);
            System.out.println("DEBUG: Work object created with ID: " + work.getId() + ", authorId: " + work.getAuthorId());
            work = workRepository.save(work);
            System.out.println("DEBUG: Work saved with ID: " + work.getId() + ", authorId: " + work.getAuthorId());
            
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(convertWorkToMap(work));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(createErrorResponse("SERVER_ERROR"));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateWork(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        try {
            Optional<Work> workOpt = workRepository.findById(id);
            if (workOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(createErrorResponse("WORK_NOT_FOUND"));
            }

            Work work = workOpt.get();
            String title = (String) request.get("title");
            String grade = (String) request.get("grade");
            String summary = (String) request.get("summary");
            String analysis = (String) request.get("analysis");
            Long authorId = null;
            
            if (request.get("author_id") != null) {
                try {
                    authorId = Long.valueOf(request.get("author_id").toString());
                } catch (Exception e) {
                    return ResponseEntity.badRequest()
                        .body(createErrorResponse("INVALID_AUTHOR_ID"));
                }
            }

            if (title != null && !title.trim().isEmpty()) {
                work.setTitle(title.trim());
            }
            if (grade != null) {
                work.setGrade(grade);
            }
            if (summary != null) {
                work.setSummary(summary);
            }
            if (analysis != null) {
                work.setAnalysis(analysis);
            }
            if (authorId != null) {
                work.setAuthorId(authorId);
            }

            work = workRepository.save(work);
            return ResponseEntity.ok(convertWorkToMap(work));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(createErrorResponse("SERVER_ERROR"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWork(@PathVariable Long id) {
        try {
            if (!workRepository.existsById(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(createErrorResponse("WORK_NOT_FOUND"));
            }

            workRepository.deleteById(id);
            return ResponseEntity.noContent().build();

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(createErrorResponse("SERVER_ERROR"));
        }
    }

    private Long getNextWorkId() {
        try {
            Long maxId = workRepository.findMaxId();
            System.out.println("DEBUG: Max Work ID found: " + maxId);
            Long nextId = (maxId != null) ? maxId + 1 : 1L;
            System.out.println("DEBUG: Next Work ID will be: " + nextId);
            return nextId;
        } catch (Exception e) {
            System.out.println("DEBUG: Error getting max Work ID: " + e.getMessage());
            // If there's an error getting max ID, start from 1
            return 1L;
        }
    }

    private Map<String, Object> convertWorkToMap(Work work) {
        Map<String, Object> workMap = new HashMap<>();
        workMap.put("id", work.getId());
        workMap.put("title", work.getTitle());
        workMap.put("grade", work.getGrade());
        workMap.put("summary", work.getSummary());
        workMap.put("analysis", work.getAnalysis());
        workMap.put("author_id", work.getAuthorId());
        workMap.put("created_at", work.getCreatedAt());
        workMap.put("updated_at", work.getUpdatedAt());
        return workMap;
    }

    private Map<String, String> createErrorResponse(String error) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", error);
        return errorResponse;
    }
}