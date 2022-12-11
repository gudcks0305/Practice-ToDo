package com.example.demo.todo.controller;

import com.example.demo.global.response.SingleResponse;
import com.example.demo.todo.entity.ToDoEntity;
import com.example.demo.todo.mapper.ToDoMapper;
import com.example.demo.todo.service.ToDoService;
import com.example.demo.todo.dto.ToDoDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Validated
@RestController
@RequestMapping("/")
public class ToDoController {
    private final ToDoService toDoService;
    private final ToDoMapper toDoMapper;

    public ToDoController(ToDoService toDoService, ToDoMapper toDoMapper) {
        this.toDoService = toDoService;
        this.toDoMapper = toDoMapper;
    }

    @PostMapping
    public ResponseEntity postToDo(@RequestBody @Valid ToDoDto.Post requestBody) {
        ToDoEntity postEntity = toDoMapper.postToDoToEntity(requestBody);
        ToDoEntity response = toDoService.saveToDo(postEntity);
        return ResponseEntity.ok(toDoMapper.entityToResponse(response));
        /*return ResponseEntity.created(URI.create("/" + response.getId()))
                .body(new SingleResponse<>(toDoMapper.entityToResponse(response)));*/
    }

    @GetMapping
    public ResponseEntity getToDoList() {
        return ResponseEntity.ok(toDoMapper.entityListToResponseList(toDoService.getToDoList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity getToDoById(@PathVariable Long id) {
        return ResponseEntity.ok(toDoMapper.entityToResponse(toDoService.getToDoById(id)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity updateToDoById(@PathVariable Long id, @RequestBody @Valid ToDoDto.Patch requestBody) {
        ToDoEntity patchEntity = toDoMapper.patchToDoToEntity(requestBody);
        ToDoEntity response = toDoService.updateToDoById(id, patchEntity);
        return ResponseEntity.ok(toDoMapper.entityToResponse(response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteToDoById(@PathVariable Long id) {
        toDoService.deleteToDoById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity deleteAllToDo() {
        toDoService.deleteToDoList();
        return ResponseEntity.ok().build();
    }
}
