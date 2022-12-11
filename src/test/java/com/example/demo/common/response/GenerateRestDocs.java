package com.example.demo.common.response;

import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class GenerateRestDocs {
    // ignore Boxed Type
    static final List<String> ignoreType
            = List.of(
                    "java.lang.Long", "java.lang.String", "java.lang.Integer",
            "java.lang.Boolean", "java.lang.Double", "java.lang.Float",
            "java.lang.Short", "java.lang.Byte", "java.lang.Character"
    );
    public static void preRestDocsSet(ResultActions resultActions  , Object expectedResponse , String fieldPrefix) throws Exception {

        Field[] fields = expectedResponse.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if(!field.getType().isPrimitive() && !ignoreType.contains(field.getType().getName()) && field.get(expectedResponse) != null){
                preRestDocsSet(resultActions,
                        List.of(field.get(expectedResponse)),
                        fieldPrefix == null ? field.getName() : fieldPrefix + "." + field.getName());

            }
            else{
                resultActions.andExpect(jsonPath(getJsonPath(fieldPrefix,field)).value(field.get(expectedResponse)));

            }
        }

    }
   public static void preRestDocsSet(ResultActions resultActions  , List<Object> expectedResponse , String fieldPrefix) throws Exception {
       for (int i = 0; i < expectedResponse.size(); i++) {
           preRestDocsSet(resultActions, expectedResponse.get(i), fieldPrefix == null ? "[" + i + "]" : fieldPrefix + "[" + i + "]");
       }
   }
   private static String getJsonPath(String fieldPrefix , Field field) {
        if (fieldPrefix == null || fieldPrefix.isEmpty()) {
             return "$." + field.getName();
        }/*else if(field.getType() == List.class || field.getType().isArray()){
             return "$." + fieldPrefix + "[0]." + field.getName();
        }*/
        return "$." + fieldPrefix + "." + field.getName();
   }
   public static RestDocumentationResultHandler generateRestDocs(String documentName , Object request,List<String> requestDic , Object response , List<String> responseDic  ) throws Exception {
       return document(documentName ,
               preprocessRequest(prettyPrint()),
               preprocessResponse(prettyPrint()),
               requestFields(customGenFields(request , requestDic)),
               responseFields(customGenFields(response , responseDic))
               );
   }
    public static RestDocumentationResultHandler generateRestDocs(String documentName , Object response , List<String> responseDic  ) throws Exception {
        return document(documentName ,
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                responseFields(customGenFields(response , responseDic))
        );
    }
    public static RestDocumentationResultHandler generateRestDocs(String documentName , List<Object> responses , List<String> responseDic  ) throws Exception {
        return document(documentName ,
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                responseFields(customGenFields(responses , responseDic))
        );
    }


   public static List<FieldDescriptor> customGenFields(Object request , List<String> requestDic) throws Exception {
         List<FieldDescriptor> fieldDescriptors = new ArrayList<>();
         Field[] fields = request.getClass().getDeclaredFields();
         for (Field field : fields) {
              field.setAccessible(true);
              if(!field.getType().isPrimitive() && !ignoreType.contains(field.getType().getName()) && field.get(request) != null){
                fieldDescriptors.addAll(customGenFields(field.get(request) , requestDic));
              }else{
                fieldDescriptors.add(getFieldDescriptor(field , requestDic));
              }
         }
         return fieldDescriptors;
   }

    private static FieldDescriptor getFieldDescriptor(Field field, List<String> requestDic) {
        for (String dic : requestDic) {
            if(dic.contains(field.getName())){
                return fieldWithPath(field.getName()).description(dic).type(getJsonType(field.getType()));
            }
        }
        return fieldWithPath(field.getName()).type(getJsonType(field.getType()));
    }
    private static JsonFieldType getJsonType(Class<?> classes){
        return switch (classes.getName()) {
            case "java.lang.Long", "java.lang.Integer", "java.lang.Double",
                    "java.lang.Float", "java.lang.Short", "java.lang.Byte" ,
                    "int" , "long" , "double" , "float" , "short" , "byte" -> JsonFieldType.NUMBER;
            case "java.lang.String", "java.lang.Character" ,"char" -> JsonFieldType.STRING;
            case "java.lang.Boolean" ,"boolean" -> JsonFieldType.BOOLEAN;
            default -> JsonFieldType.NULL;
        };
    }
}
