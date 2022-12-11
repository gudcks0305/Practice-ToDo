package com.example.demo.todo.config;

import com.example.demo.todo.repository.MemoryToDoRepository;
import com.example.demo.todo.repository.ToDoRepository;
import com.example.demo.todo.service.MemoryToDoService;
import com.example.demo.todo.service.ToDoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
//@Profile("mock")
public class ToDoBeanConfig {
    @Bean
    public ToDoService toDoService() {
        return new MemoryToDoService(toDoRepositoryInterface());
    }

    @Bean
    public ToDoRepository toDoRepositoryInterface() {
        return new MemoryToDoRepository();
    }
}
