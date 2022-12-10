package com.example.demo.todo.controller;

import com.example.demo.todo.mapper.ToDoMapper;
import com.example.demo.todo.service.ToDoService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
@WebMvcTest(ToDoController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class ToDoControllerTest {

    @MockBean
    private ToDoService toDoService;
    @MockBean
    private ToDoMapper mapper;

    @Test
    void postToDo() {
    }

    @Test
    void getToDoList() {
    }

    @Test
    void getToDoById() {
    }

    @Test
    void updateToDoById() {
    }

    @Test
    void deleteToDoById() {
    }

    @Test
    void deleteAllToDo() {
    }
}