package com.example.demo.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.util.Date;

@Getter
public class ToDoDto {
    @Getter
    @AllArgsConstructor
    @ToString
    public static class Post {
        @NonNull
        private String title;
        private int order;
        private boolean completed;

    }
    @AllArgsConstructor
    @Getter
    @ToString
    public static class Patch {
        @NonNull
        private String title;
        private int order;
        private boolean completed;

    }
    @Getter
    @AllArgsConstructor
    @ToString
    public static class Response{
        private Long id;
        private String title;
        private Integer order;
        private boolean completed;
        private Date createdAt;
        private Date modifiedAt;




    }
}
