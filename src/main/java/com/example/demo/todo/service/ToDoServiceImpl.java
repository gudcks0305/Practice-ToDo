package com.example.demo.todo.service;

import com.example.demo.global.exception.CustomLogicException;
import com.example.demo.global.exception.ExceptionCode;
import com.example.demo.global.utils.CustomBeanUtils;
import com.example.demo.todo.entity.ToDoEntity;
import com.example.demo.todo.repository.JpaToDoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ToDoServiceImpl implements ToDoService {

    private final JpaToDoRepository toDoRepository;
    private final CustomBeanUtils<ToDoEntity> customBeanUtils;

    public ToDoServiceImpl(JpaToDoRepository toDoRepository, CustomBeanUtils<ToDoEntity> customBeanUtils) {
        this.toDoRepository = toDoRepository;
        this.customBeanUtils = customBeanUtils;
    }

    @Override
    public ToDoEntity getToDoById(Long id) {
        return toDoRepository.findById(id).orElseThrow(() -> new CustomLogicException(ExceptionCode.TODO_NONE));
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
        ToDoEntity findTodos = toDoRepository.findById(id).orElseThrow();
        customBeanUtils.copyNonNullProperties(toDoEntity, findTodos);
        return findTodos;
    }
}
