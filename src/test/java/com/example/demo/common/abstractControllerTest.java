package com.example.demo.common;

import com.example.demo.todo.controller.ToDoController;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;


@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public abstract class abstractControllerTest {
   @Autowired
   public MockMvc mockMvc;

}
