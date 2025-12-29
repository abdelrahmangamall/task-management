package com.task.task_management.service;


import com.task.task_management.dto.TaskRequest;
import com.task.task_management.dto.TaskResponse;
import com.task.task_management.dto.TaskUpdateRequest;
import com.task.task_management.entity.Task;
import com.task.task_management.entity.TaskStatus;
import com.task.task_management.entity.User;
import com.task.task_management.repository.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserDetailsServiceImpl userDetailsService;

    public TaskService(TaskRepository taskRepository, UserDetailsServiceImpl userDetailsService) {
        this.taskRepository = taskRepository;
        this.userDetailsService = userDetailsService;
    }

    public TaskResponse createTask(TaskRequest request, String userEmail) {
        User user = userDetailsService.getUserByEmail(userEmail);

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus() != null ? request.getStatus() : TaskStatus.PENDING);
        task.setUser(user);

        Task savedTask = taskRepository.save(task);
        return mapToResponse(savedTask);
    }

    public Page<TaskResponse> getUserTasks(String userEmail, int page, int size) {
        User user = userDetailsService.getUserByEmail(userEmail);
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        return taskRepository.findByUserId(user.getId(), pageable)
                .map(this::mapToResponse);
    }

    public TaskResponse getTaskById(Long taskId, String userEmail) {
        User user = userDetailsService.getUserByEmail(userEmail);
        Task task = taskRepository.findByIdAndUserId(taskId, user.getId())
                .orElseThrow(() -> new RuntimeException("Task not found"));

        return mapToResponse(task);
    }

    public TaskResponse updateTask(Long taskId, TaskUpdateRequest request, String userEmail) {
        User user = userDetailsService.getUserByEmail(userEmail);
        Task task = taskRepository.findByIdAndUserId(taskId, user.getId())
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (request.getTitle() != null) {
            task.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            task.setDescription(request.getDescription());
        }
        if (request.getStatus() != null) {
            task.setStatus(request.getStatus());
        }

        Task updatedTask = taskRepository.save(task);
        return mapToResponse(updatedTask);
    }

    @Transactional
    public void deleteTask(Long taskId, String userEmail) {
        User user = userDetailsService.getUserByEmail(userEmail);
        Task task = taskRepository.findByIdAndUserId(taskId, user.getId())
                .orElseThrow(() -> new RuntimeException("Task not found"));

        taskRepository.delete(task);
    }

    private TaskResponse mapToResponse(Task task) {
        TaskResponse response = new TaskResponse();
        response.setId(task.getId());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setStatus(task.getStatus().getValue());
        response.setCreatedAt(task.getCreatedAt());
        response.setUserId(task.getUser().getId());
        return response;
    }
}
