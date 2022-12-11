package com.example.demo.todo.mapper;

import com.example.demo.todo.entity.ToDoEntity;
import com.example.demo.todo.dto.ToDoDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ToDoMapper {
    ToDoEntity postToDoToEntity(ToDoDto.Post requestBody);

    ToDoDto.Response entityToResponse(ToDoEntity entity);

    ToDoEntity patchToDoToEntity(ToDoDto.Patch requestBody);

    List<ToDoDto.Response> entityListToResponseList(List<ToDoEntity> toDoList);
}
