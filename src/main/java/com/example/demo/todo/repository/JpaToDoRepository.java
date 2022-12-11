package com.example.demo.todo.repository;

import com.example.demo.todo.entity.ToDoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaToDoRepository
        extends JpaRepository<ToDoEntity, Long> {


}
