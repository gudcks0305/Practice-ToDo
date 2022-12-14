package com.example.demo.todo.controller;

import com.example.demo.common.abstractControllerTest;
import com.example.demo.common.request.MockRequestFactory;
import com.example.demo.todo.data.ToDoDataFactory;
import com.example.demo.todo.entity.ToDoEntity;
import com.example.demo.todo.mapper.ToDoMapper;
import com.example.demo.todo.dto.ToDoDto;
import com.example.demo.todo.service.ToDoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.demo.common.request.MockRequestFactory.patchRequest;
import static com.example.demo.common.request.MockRequestFactory.postRequest;
import static com.example.demo.common.response.GenerateRestDocs.generateRestDocs;
import static com.example.demo.common.response.GenerateRestDocs.preRestDocsSet;
import static com.example.demo.common.utils.RandomIndexGenerate.getRandomIndex;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ToDoController.class)
//@ExtendWith(MockitoExtension.class)
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
        preRestDocsSet(result, response, null);
        result.andExpect(status().isOk());
        result.andDo(generateRestDocs("todo-post",
                        post, List.of(
                                "title: ?????? ??????", "order: ??? ??? ??????", "completed: ?????? ?????? ??????"
                        ),
                        response, List.of(
                                "id: ?????? ?????? ??????", "title: ?????? ??????", "order: ??? ??? ??????", "completed: ?????? ?????? ??????", "createdAt: ?????? ?????? ??????", "modifiedAt: ?????? ?????? ??????"
                        ))
                //response, Arrays.stream(response.getClass().getDeclaredFields()).map(field -> field.getName() + ": " + field.getType().getSimpleName()).toList()
        );

    }


    @Test
    void getToDoList() throws Exception {
        List<ToDoDto.Response> responses = ToDoDataFactory.POST_RESPONSE_LIST;
        given(toDoService.getToDoList()).willReturn(new ArrayList<>());
        given(mapper.entityListToResponseList(Mockito.anyList())).willReturn(responses);
        given(mapper.entityToResponse(Mockito.any(ToDoEntity.class))).willReturn(responses.get(0));

        // when
        ResultActions result = MockRequestFactory.getRequest(mockMvc, "/");
        preRestDocsSet(result, responses.stream().map(response -> (Object) response).collect(Collectors.toList()), null);
        result.andExpect(status().isOk());

        result.andDo(document(
                "todo-get",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                responseFields(
                        fieldWithPath("[]").type(JsonFieldType.ARRAY).description("?????? ?????????"),
                        fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("?????? ?????? ??????"),
                        fieldWithPath("[].title").type(JsonFieldType.STRING).description("?????? ??????"),
                        fieldWithPath("[].order").type(JsonFieldType.NUMBER).description("??? ??? ??????"),
                        fieldWithPath("[].completed").type(JsonFieldType.BOOLEAN).description("?????? ?????? ??????"),
                        fieldWithPath("[].createdAt").type(JsonFieldType.NULL).description("?????? ?????? ??????"),
                        fieldWithPath("[].modifiedAt").type(JsonFieldType.NULL).description("?????? ?????? ??????")
                ))
        );


    }

    @Test
    void getToDoById() throws Exception {
        int RandomNums = getRandomIndex(ToDoDataFactory.POST_RESPONSE_LIST.size());
        ToDoDto.Response response = ToDoDataFactory.POST_RESPONSE_LIST.get(RandomNums);
        given(toDoService.getToDoById(Mockito.anyLong())).willReturn(new ToDoEntity());
        given(mapper.entityToResponse(Mockito.any(ToDoEntity.class))).willReturn(response);

        // when
        ResultActions result = MockRequestFactory.getRequest(mockMvc, "/" + response.getId());
        preRestDocsSet(result, response, null);
        result.andExpect(status().isOk());
        result.andDo(generateRestDocs("todo-get-by-id",
                response, List.of(
                        "id: ?????? ?????? ??????", "title: ?????? ??????", "order: ??? ??? ??????", "completed: ?????? ?????? ??????", "createdAt: ?????? ?????? ??????", "modifiedAt: ?????? ?????? ??????"
                ))
        );
    }

    @Test
    void updateToDoById() throws Exception {
        int RandomNums = getRandomIndex(ToDoDataFactory.PATCH_RESPONSE_LIST.size());
        ToDoDto.Response response = ToDoDataFactory.PATCH_RESPONSE_LIST.get(RandomNums);
        given(mapper.patchToDoToEntity(Mockito.any(ToDoDto.Patch.class))).willReturn(new ToDoEntity());
        given(toDoService.updateToDoById(Mockito.anyLong(), Mockito.any(ToDoEntity.class))).willReturn(new ToDoEntity());
        given(mapper.entityToResponse(Mockito.any(ToDoEntity.class))).willReturn(response);
        // when
        ResultActions result = patchRequest(mockMvc, "/" + response.getId(), response);
        // then
        preRestDocsSet(result, response, null);
        result.andExpect(status().isOk());
        result.andDo(generateRestDocs("todo-patch",
                response, List.of(
                        "id: ?????? ?????? ??????", "title: ?????? ??????", "order: ??? ??? ??????", "completed: ?????? ?????? ??????",
                        "createdAt: ?????? ?????? ??????", "modifiedAt: ?????? ?????? ??????"
                ))
        );
    }

    @Test
    void deleteToDoById() throws Exception {
        int RandomNums = getRandomIndex(ToDoDataFactory.POST_RESPONSE_LIST.size());
        ToDoDto.Response response = ToDoDataFactory.POST_RESPONSE_LIST.get(RandomNums);
        ResultActions result = MockRequestFactory.deleteRequest(mockMvc, "/" + response.getId());
        result.andExpect(status().isOk());

        result.andDo(document(
                "todo-delete",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())
        ));
    }

    @Test
    void deleteAllToDo() throws Exception {
        ResultActions result = MockRequestFactory.deleteRequest(mockMvc, "/");
        result.andExpect(status().isOk());
        result.andDo(document(
                "todo-delete-all",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())
        ));
    }
}