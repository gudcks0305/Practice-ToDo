package com.example.demo.todo.entity;

import com.example.demo.global.audit.Auditable;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class ToDoEntity extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TODO_ID")
    private Long id;
    private String title;
    @Column(name = "TODO_ORDER" , columnDefinition = "integer default 0")
    private Integer order;
    private boolean completed;
}
