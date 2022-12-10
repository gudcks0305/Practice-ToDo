package com.example.demo.todo.entity;

import com.example.global.Auditable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ToDoEntity extends Auditable {
    @Id
    private Long id;
    private String title;

    private int order;
    private boolean completed;
}
