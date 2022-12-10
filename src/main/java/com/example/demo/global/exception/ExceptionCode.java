package com.example.demo.global.exception;

import lombok.Getter;

public enum ExceptionCode {

   TITLE_NONE(400, "TITLE_NONE");
   @Getter
   private int code;

   @Getter
   private String message;

   ExceptionCode(int code, String message) {
      this.code = code;
      this.message = message;
   }
}
