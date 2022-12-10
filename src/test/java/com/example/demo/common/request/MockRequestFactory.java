package com.example.demo.common.request;

import com.example.demo.todo.entity.ToDoEntity;
import com.google.gson.Gson;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;


public class MockRequestFactory {
   public static ResultActions postRequest(MockMvc mvc, String url, Object request) throws Exception {
      return mvc.perform(
              post(url)
                      .accept(MediaType.APPLICATION_JSON)
              .contentType(MediaType.APPLICATION_JSON)
              .content(new Gson().toJson(request)));
   }
   public static ResultActions postRequestWithParams(MockMvc mvc, String url, Object request, Map<String,Object> params) throws Exception {
      MockHttpServletRequestBuilder resultActions =  post(url)
              .accept(MediaType.APPLICATION_JSON)
              .contentType(MediaType.APPLICATION_JSON)
              .content(new Gson().toJson(request));
        for (String key : params.keySet()) {
            resultActions.param(key, params.get(key).toString());
        }
        return mvc.perform(resultActions);
   }
   public static ResultActions getRequest(MockMvc mvc, String url) throws Exception {
      return mvc.perform(
              get(createURI(url))
                      .accept(MediaType.APPLICATION_JSON)
              .contentType(MediaType.APPLICATION_JSON));
   }
   public static ResultActions getRequestWithParams(MockMvc mvc, String url, Map<String,Object> params) throws Exception {
      MockHttpServletRequestBuilder resultActions =  get(createURI(url))
              .accept(MediaType.APPLICATION_JSON)
              .contentType(MediaType.APPLICATION_JSON);
        for (String key : params.keySet()) {
            resultActions.param(key, params.get(key).toString());
        }
        return mvc.perform(resultActions);
   }
   public static URI createURI(String url) {
      return UriComponentsBuilder.newInstance().path(url).build().toUri();
   }

   public static URI createURI(String url, long resourceId) {
      return UriComponentsBuilder.newInstance().path(url).buildAndExpand(resourceId).toUri();
   }
}
