package com.example.demo.todo.controller;

import com.example.demo.common.abstractControllerTest;
import com.example.demo.common.request.MockRequestFactory;
import com.example.demo.common.response.GenerateRestDocs;
import com.example.demo.common.utils.RandomIndexGenerate;
import com.example.demo.todo.data.ToDoDataFactory;
import com.example.demo.todo.entity.ToDoEntity;
import com.example.demo.todo.mapper.ToDoMapper;
import com.example.demo.todo.service.ToDoService;
import com.example.demo.todo.dto.ToDoDto;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.example.demo.common.request.MockRequestFactory.postRequest;
import static com.example.demo.common.response.GenerateRestDocs.generateRestDocs;
import static com.example.demo.common.response.GenerateRestDocs.preRestDocsSet;
import static com.example.demo.common.utils.RandomIndexGenerate.getRandomIndex;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ToDoController.class)
//@MockBean(JpaMetamodelMappingContext.class)
public class ToDoControllerTest extends abstractControllerTest {

    @MockBean
    private ToDoService toDoService;
    @MockBean
    private ToDoMapper mapper;

    @Test
    void postToDo() throws Exception {
        //given
        int RandomNums = getRandomIndex(ToDoDataFactory.POST_LIST.size());
        ToDoDto.Post post = ToDoDataFactory.POST_LIST.get(RandomNums);
        ToDoDto.Response response = ToDoDataFactory.POST_RESPONSE_LIST.get(RandomNums);

        //when
        given(mapper.entityToResponse(Mockito.any(ToDoEntity.class))).willReturn(response);
        given(mapper.postToDoToEntity(Mockito.any(ToDoDto.Post.class))).willReturn(new ToDoEntity());
        given(toDoService.saveToDo(Mockito.any(ToDoEntity.class))).willReturn(new ToDoEntity());

        //then
        ResultActions result = postRequest(mockMvc, "/", post);
        preRestDocsSet(result, List.of(response), null);
        result.andExpect(status().isOk());
        result.andDo(generateRestDocs("todo-post",
                post, List.of(
                        "title: 할일 제목", "order: 할 일 순서", "completed: 할일 완료 여부"
                ),
                response, List.of(
                        "id: 할일 고유 번호", "title: 할일 제목", "order: 할 일 순서", "completed: 할일 완료 여부", "createdAt: 할일 생성 시간", "modifiedAt: 할일 수정 시간"
                ))
                //response, Arrays.stream(response.getClass().getDeclaredFields()).map(field -> field.getName() + ": " + field.getType().getSimpleName()).toList()
        );

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