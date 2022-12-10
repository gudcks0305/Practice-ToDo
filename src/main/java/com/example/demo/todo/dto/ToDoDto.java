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
    @AllArgsConstructor
    @Getter
    public static class Patch {
        @NonNull
        private String title;
        private int order;
        private boolean completed;

    }
    @Getter
    @AllArgsConstructor
    public static class Response{
        private Long id;
        private String title;
        private Integer order;
        private boolean completed;
        private String createdAt;
        private String modifiedAt;




    }
}
