package com.example.demo.todo.repository;

import com.example.demo.todo.entity.ToDoEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MemoryToDoRepository implements ToDoRepository {
   public List<ToDoEntity> toDoList = new ArrayList<>(
           List.of(
                   new ToDoEntity(1L, "title1", 1, false),
                   new ToDoEntity(2L, "title2", 2, false),
                   new ToDoEntity(3L, "title3", 3, false)
           )
   );

    @Override
    public Optional<ToDoEntity> findById(Long id) {
        return toDoList.stream()
                .filter(toDoEntity -> toDoEntity.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<ToDoEntity> findAll() {
        return toDoList;
    }

    @Override
    public ToDoEntity save(ToDoEntity toDoEntity) {
        return toDoEntity;
    }

    @Override
    public void deleteById(Long id) {
        toDoList.removeIf(toDoEntity -> toDoEntity.getId().equals(id));
    }

    @Override
    public void deleteAll() {
        toDoList.clear();
    }
}
