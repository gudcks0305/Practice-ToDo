package com.example.demo.global.response;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ListResponse<T> {
   List<T> data;
}
