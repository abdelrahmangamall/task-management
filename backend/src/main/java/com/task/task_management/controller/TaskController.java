package com.task.task_management.controller;


import com.task.task_management.dto.TaskRequest;
import com.task.task_management.dto.TaskResponse;
import com.task.task_management.dto.TaskUpdateRequest;
import com.task.task_management.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/tasks")
@CrossOrigin(origins = "*")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<?> createTask(
            @Valid @RequestBody TaskRequest request,
            Authentication authentication
    ) {
        String userEmail = authentication.getName();
        TaskResponse response = taskService.createTask(request, userEmail);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<?> getTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Authentication authentication
    ) {
        String userEmail = authentication.getName();
        return ResponseEntity.ok(taskService.getUserTasks(userEmail, page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTask(
            @PathVariable Long id,
            Authentication authentication
    ) {
        String userEmail = authentication.getName();
        return ResponseEntity.ok(taskService.getTaskById(id, userEmail));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskUpdateRequest request,
            Authentication authentication
    ) {
        String userEmail = authentication.getName();
        return ResponseEntity.ok(taskService.updateTask(id, request, userEmail));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(
            @PathVariable Long id,
            Authentication authentication
    ) {
        String userEmail = authentication.getName();
        taskService.deleteTask(id, userEmail);
        return ResponseEntity.ok(Map.of("message", "Task deleted successfully"));
    }
}