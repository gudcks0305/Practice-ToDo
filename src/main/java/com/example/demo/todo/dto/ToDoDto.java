package com.example.demo.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class ToDoDto {
    @Getter
    @AllArgsConstructor
    public static class Post {
        @NonNull
        private String title;
        private int order;
        private boolean completed;

    }
}
