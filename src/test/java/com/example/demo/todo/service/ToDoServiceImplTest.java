package com.example.demo.todo.service;

import com.example.demo.common.utils.RandomIndexGenerate;
import com.example.demo.global.utils.CustomBeanUtils;
import com.example.demo.todo.data.ToDoDataFactory;
import com.example.demo.todo.dto.ToDoDto;
import com.example.demo.todo.entity.ToDoEntity;
import com.example.demo.todo.repository.JpaToDoRepository;
import com.example.demo.todo.repository.ToDoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.example.demo.common.utils.RandomIndexGenerate.getRandomIndex;
import static com.example.demo.todo.data.ToDoDataFactory.ENTITY_LIST;
import static com.example.demo.todo.data.ToDoDataFactory.POST_LIST;
import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
public class ToDoServiceImplTest {
    @InjectMocks
    private ToDoServiceImpl toDoService;
    @Mock
    private JpaToDoRepository toDoRepository;
    @Mock
    private CustomBeanUtils<ToDoEntity> customBeanUtils;
    @Test
    void getToDoById() {
        //given
        int randomNumber = getRandomIndex(ENTITY_LIST.size());
        ToDoEntity toDoEntity = ENTITY_LIST.get(randomNumber);
        given(toDoRepository.findById(Mockito.anyLong())).willReturn(Optional.of(toDoEntity));
        //when
        ToDoEntity returnEntity = toDoService.getToDoById(toDoEntity.getId());
        //then
        assertEquals(toDoEntity.getId(), returnEntity.getId());
        assertEquals(toDoEntity.getTitle(), returnEntity.getTitle());
        assertEquals(toDoEntity.getCreatedAt(), returnEntity.getCreatedAt());

    }

    @Test
    void getToDoList() {
        //given
        given(toDoRepository.findAll()).willReturn(ENTITY_LIST);
        //when
        List<ToDoEntity> returnEntityList = toDoService.getToDoList();
        //then
        assertEquals(ENTITY_LIST.size(), returnEntityList.size());
        assertEquals(ENTITY_LIST.get(0).getId(), returnEntityList.get(0).getId());
        assertEquals(ENTITY_LIST.get(0).getTitle(), returnEntityList.get(0).getTitle());
    }

    @Test
    void deleteToDoById() {
        //given
        int randomNumber = getRandomIndex(ENTITY_LIST.size());
        ToDoEntity toDoEntity = ENTITY_LIST.get(randomNumber);
        List<ToDoEntity> givenList = ENTITY_LIST.stream().filter(entity -> !Objects.equals(toDoEntity.getId(), entity.getId())).toList();
        given(toDoRepository.findAll()).willReturn(givenList);
        //when
        toDoService.deleteToDoById(toDoEntity.getId());
        //then
        assertEquals(ENTITY_LIST.size()-1, toDoService.getToDoList().size());
    }

    @Test
    void deleteToDoList() {
        //given
        given(toDoRepository.findAll()).willReturn(new ArrayList<>());
        //when
        toDoService.deleteToDoList();
        //then
        assertEquals(0, toDoService.getToDoList().size());
    }

    @Test
    void saveToDo() {
        //given
        int randomNumber = getRandomIndex(POST_LIST.size());
        ToDoEntity toDoEntity = ENTITY_LIST.get(randomNumber);
        given(toDoRepository.save(Mockito.any(ToDoEntity.class))).willReturn(toDoEntity);
        //when
        ToDoEntity returnEntity = toDoService.saveToDo(toDoEntity);
        //then
        assertEquals(toDoEntity.getId(), returnEntity.getId());
        assertEquals(toDoEntity.getTitle(), returnEntity.getTitle());
        assertEquals(toDoEntity.getCreatedAt(), returnEntity.getCreatedAt());
    }

    @Test
    void updateToDoById() {
        //given
        int randomNumber = getRandomIndex(ENTITY_LIST.size());
        ToDoEntity toDoEntity = ENTITY_LIST.get(randomNumber);
        given(toDoRepository.findById(Mockito.anyLong())).willReturn(Optional.of(toDoEntity));
        given(customBeanUtils.copyNonNullProperties(Mockito.any(), Mockito.any())).willReturn(toDoEntity);
        //when
        ToDoEntity returnEntity = toDoService.updateToDoById(toDoEntity.getId(), toDoEntity);
        //then
        assertEquals(toDoEntity.getId(), returnEntity.getId());
        assertEquals(toDoEntity.getTitle(), returnEntity.getTitle());
        assertEquals(toDoEntity.getCreatedAt(), returnEntity.getCreatedAt());
    }
}