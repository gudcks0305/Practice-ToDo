package com.example.demo.todo.service;

import com.example.demo.todo.entity.ToDoEntity;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ToDoService {
    // get
    ToDoEntity getToDoById(Long id);

    List<ToDoEntity> getToDoList();

    //delete
    void deleteToDoById(Long id);

    void deleteToDoList();
    // post
    ToDoEntity saveToDo(ToDoEntity toDoEntity);
    // patch
    ToDoEntity updateToDoById(Long id, ToDoEntity toDoEntity);
}
