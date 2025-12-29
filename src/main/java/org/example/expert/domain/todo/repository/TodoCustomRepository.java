package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface TodoCustomRepository {
// - Custom Methods
    Page<Todo> searchTodos(
            String weather,
            LocalDateTime start,
            LocalDateTime end,
            Pageable pageable
    );
}
