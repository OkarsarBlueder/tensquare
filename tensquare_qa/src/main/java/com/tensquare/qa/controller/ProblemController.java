package com.tensquare.qa.controller;

import com.tensquare.qa.dao.ProblemDao;
import com.tensquare.qa.service.ProblemService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utils.IdWorker;

@RestController
@RequestMapping("/problem")
public class ProblemController {

  @Autowired
  ProblemService problemService;

  @Autowired
  IdWorker idWorker;
  @GetMapping
  public Result findAll(){
      return new Result(true, StatusCode.OK,"查询成功", problemService.findAll());
  }
}
