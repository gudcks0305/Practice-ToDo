package com.example.demo.todo.service;

import com.example.demo.todo.entity.ToDoEntity;
import com.example.demo.todo.repository.ToDoRepository;

import java.util.List;
public class MemoryToDoService implements ToDoService {

    private final ToDoRepository toDoRepository;

    public MemoryToDoService(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

    @Override
    public ToDoEntity getToDoById(Long id) {
        return toDoRepository.findById(id).orElseThrow();
    }

    @Override
    public List<ToDoEntity> getToDoList() {
        return toDoRepository.findAll();
    }

    @Override
    public void deleteToDoById(Long id) {
        toDoRepository.deleteById(id);
    }

    @Override
    public void deleteToDoList() {
        toDoRepository.deleteAll();
    }

    @Override
    public ToDoEntity saveToDo(ToDoEntity toDoEntity) {
        toDoEntity.setId(toDoRepository.findAll().size() + 1L);
        toDoRepository.save(toDoEntity);
        return toDoEntity;
    }

    @Override
    public ToDoEntity updateToDoById(Long id, ToDoEntity toDoEntity) {
        return toDoRepository.save(toDoEntity);
    }
}
