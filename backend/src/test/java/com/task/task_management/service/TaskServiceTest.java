package com.task.task_management.service;

import com.task.task_management.dto.TaskRequest;
import com.task.task_management.dto.TaskResponse;
import com.task.task_management.dto.TaskUpdateRequest;
import com.task.task_management.entity.Task;
import com.task.task_management.entity.TaskStatus;
import com.task.task_management.entity.User;
import com.task.task_management.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @InjectMocks
    private TaskService taskService;

    private User testUser;
    private Task testTask;
    private TaskRequest taskRequest;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setName("John Doe");
        testUser.setEmail("john@example.com");

        testTask = new Task();
        testTask.setId(1L);
        testTask.setTitle("Test Task");
        testTask.setDescription("Test Description");
        testTask.setStatus(TaskStatus.PENDING);
        testTask.setCreatedAt(LocalDateTime.now());
        testTask.setUser(testUser);

        taskRequest = new TaskRequest();
        taskRequest.setTitle("New Task");
        taskRequest.setDescription("New Description");
        taskRequest.setStatus(TaskStatus.PENDING);
    }

    @Test
    void createTask_Success() {
        // Arrange
        when(userDetailsService.getUserByEmail("john@example.com")).thenReturn(testUser);
        when(taskRepository.save(any(Task.class))).thenReturn(testTask);

        // Act
        TaskResponse response = taskService.createTask(taskRequest, "john@example.com");

        // Assert
        assertNotNull(response);
        assertEquals(testTask.getId(), response.getId());
        assertEquals(testTask.getTitle(), response.getTitle());
        assertEquals(testTask.getDescription(), response.getDescription());
        assertEquals(testTask.getStatus().getValue(), response.getStatus());

        verify(userDetailsService).getUserByEmail("john@example.com");
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void getUserTasks_Success() {
        // Arrange
        Task task2 = new Task();
        task2.setId(2L);
        task2.setTitle("Task 2");
        task2.setStatus(TaskStatus.IN_PROGRESS);
        task2.setUser(testUser);
        task2.setCreatedAt(LocalDateTime.now());

        Page<Task> taskPage = new PageImpl<>(Arrays.asList(testTask, task2));

        when(userDetailsService.getUserByEmail("john@example.com")).thenReturn(testUser);
        when(taskRepository.findByUserId(eq(1L), any(Pageable.class))).thenReturn(taskPage);

        // Act
        Page<TaskResponse> response = taskService.getUserTasks("john@example.com", 0, 10);

        // Assert
        assertNotNull(response);
        assertEquals(2, response.getContent().size());
        assertEquals("Test Task", response.getContent().get(0).getTitle());
        assertEquals("Task 2", response.getContent().get(1).getTitle());

        verify(userDetailsService).getUserByEmail("john@example.com");
        verify(taskRepository).findByUserId(eq(1L), any(Pageable.class));
    }

    @Test
    void getTaskById_Success() {
        // Arrange
        when(userDetailsService.getUserByEmail("john@example.com")).thenReturn(testUser);
        when(taskRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.of(testTask));

        // Act
        TaskResponse response = taskService.getTaskById(1L, "john@example.com");

        // Assert
        assertNotNull(response);
        assertEquals(testTask.getId(), response.getId());
        assertEquals(testTask.getTitle(), response.getTitle());

        verify(userDetailsService).getUserByEmail("john@example.com");
        verify(taskRepository).findByIdAndUserId(1L, 1L);
    }

    @Test
    void getTaskById_NotFound_ThrowsException() {
        // Arrange
        when(userDetailsService.getUserByEmail("john@example.com")).thenReturn(testUser);
        when(taskRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            taskService.getTaskById(1L, "john@example.com");
        });

        assertEquals("Task not found", exception.getMessage());
        verify(userDetailsService).getUserByEmail("john@example.com");
        verify(taskRepository).findByIdAndUserId(1L, 1L);
    }

    @Test
    void updateTask_Success() {
        // Arrange
        TaskUpdateRequest updateRequest = new TaskUpdateRequest();
        updateRequest.setTitle("Updated Title");
        updateRequest.setStatus(TaskStatus.DONE);

        when(userDetailsService.getUserByEmail("john@example.com")).thenReturn(testUser);
        when(taskRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.of(testTask));
        when(taskRepository.save(any(Task.class))).thenReturn(testTask);

        // Act
        TaskResponse response = taskService.updateTask(1L, updateRequest, "john@example.com");

        // Assert
        assertNotNull(response);
        verify(userDetailsService).getUserByEmail("john@example.com");
        verify(taskRepository).findByIdAndUserId(1L, 1L);
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void deleteTask_Success() {
        // Arrange
        when(userDetailsService.getUserByEmail("john@example.com")).thenReturn(testUser);
        when(taskRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.of(testTask));
        doNothing().when(taskRepository).delete(testTask);

        // Act
        taskService.deleteTask(1L, "john@example.com");

        // Assert
        verify(userDetailsService).getUserByEmail("john@example.com");
        verify(taskRepository).findByIdAndUserId(1L, 1L);
        verify(taskRepository).delete(testTask);
    }

    @Test
    void deleteTask_NotFound_ThrowsException() {
        // Arrange
        when(userDetailsService.getUserByEmail("john@example.com")).thenReturn(testUser);
        when(taskRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            taskService.deleteTask(1L, "john@example.com");
        });

        assertEquals("Task not found", exception.getMessage());
        verify(userDetailsService).getUserByEmail("john@example.com");
        verify(taskRepository).findByIdAndUserId(1L, 1L);
        verify(taskRepository, never()).delete(any(Task.class));
    }
}