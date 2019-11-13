package com.tensquare.article.controller;

import entity.Result;
import entity.StatusCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BaseExceptionController {
  @ExceptionHandler(Exception.class)
  @ResponseBody
  public Result error(Exception e){
    return new Result(false, StatusCode.ERROR,e.getMessage());
  }
}
