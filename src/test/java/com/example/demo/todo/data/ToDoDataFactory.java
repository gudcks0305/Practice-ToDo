package com.example.demo.todo.data;

import com.example.demo.todo.dto.ToDoDto;
import com.example.demo.todo.entity.ToDoEntity;

import java.util.List;

public class ToDoDataFactory {
   public static final List<ToDoDto.Post> POST_LIST = List.of(
           new ToDoDto.Post("title1", 1 , false),
           new ToDoDto.Post("title2", 2, false),
           new ToDoDto.Post("title3", 3,false)
   );
   public static final List<ToDoDto.Response> POST_RESPONSE_LIST = List.of(
           new ToDoDto.Response(1L, "title1", 1, false , null, null),
           new ToDoDto.Response(2L, "title2", 2, false , null, null),
           new ToDoDto.Response(3L, "title3", 3, false , null, null)
    );
    public static final List<ToDoDto.Patch> PATCH_LIST = List.of(
              new ToDoDto.Patch("title1", 1 , true),
              new ToDoDto.Patch("title2patch", 2, false),
              new ToDoDto.Patch("title3patch", 3,true)
    );
    public static final List<ToDoDto.Response> PATCH_RESPONSE_LIST = List.of(
              new ToDoDto.Response(1L, "title1", 1, true , null, null),
              new ToDoDto.Response(2L, "title2patch", 2, false , null, null),
              new ToDoDto.Response(3L, "title3patch", 3, true , null, null)
    );
    public static final List<ToDoEntity> ENTITY_LIST = List.of(
            new ToDoEntity(1L, "title1", 1, false ),
            new ToDoEntity(2L, "title2", 2, false ),
            new ToDoEntity(3L, "title3", 3, false)
    );


}
