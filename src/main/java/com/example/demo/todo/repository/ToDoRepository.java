package com.example.demo.todo.repository;

import com.example.demo.todo.entity.ToDoEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ToDoRepository {
    Optional<ToDoEntity> findById(Long id);

    List<ToDoEntity> findAll();

    ToDoEntity save(ToDoEntity toDoEntity);

    void deleteById(Long id);

    void deleteAll();

}
