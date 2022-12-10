package com.example.demo.global.response;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SingleResponse<T> {
   private T data;
}
