package com.example.demo.todo.controller;

import com.example.demo.common.abstractControllerTest;
import com.example.demo.common.request.MockRequestFactory;
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

import java.util.Random;

import static com.example.demo.common.request.MockRequestFactory.postRequest;
import static com.example.demo.common.utils.RandomIndexGenerate.getRandomIndex;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ToDoController.class)
//@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
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
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.getId()))
                .andExpect(jsonPath("$.title").value(response.getTitle()))
                .andExpect(jsonPath("$.order").value(response.getOrder()))
                .andExpect(jsonPath("$.completed").value(response.isCompleted()))
                .andDo(document("todo-post",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("title").description("할 일 제목").type(JsonFieldType.STRING),
                                fieldWithPath("completed").description("할 일 완료 여부").type(JsonFieldType.BOOLEAN),
                                fieldWithPath("order").description("할 일 순서").type(JsonFieldType.NUMBER)
                        ),
                        responseFields(
                                fieldWithPath("id").description("할 일 고유 번호").type(JsonFieldType.NUMBER),
                                fieldWithPath("title").description("할 일 제목").type(JsonFieldType.STRING),
                                fieldWithPath("order").description("할 일 순서").type(JsonFieldType.NUMBER),
                                fieldWithPath("completed").description("할 일 완료 여부").type(JsonFieldType.BOOLEAN),
                                fieldWithPath("createdAt").description("할 일 생성 시간").type(JsonFieldType.NULL),
                                fieldWithPath("modifiedAt").description("할 일 수정 시간").type(JsonFieldType.NULL)
                        )
                        )
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