package com.tensquare.recruit.controller;

import com.tensquare.recruit.pojo.Enterprise;
import com.tensquare.recruit.service.EnterpriseService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/enterprise")
public class EnterpriseController {

  @Autowired
  private EnterpriseService enterpriseService;

  @GetMapping("/search/hotlist")
  public Result hotList(){
    List<Enterprise> enterprises = enterpriseService.hostList("1");
    return new Result(true, StatusCode.OK,"查询成功",enterprises);
  }

  @GetMapping
  public Result findAll(){
    return new Result(true,StatusCode.OK,"查询成功",enterpriseService.findAll());
  }
  @GetMapping("/{enterpriseId}")
  public Result findById(@PathVariable("enterpriseId") String id){
    return new Result(true,StatusCode.OK,"查询成功",enterpriseService.findById(id));
  }
  @PutMapping("/{enterpriseId}")
  public Result findById(@PathVariable("enterpriseId") String id, @RequestBody Enterprise enterprise){
    enterprise.setId(id);
    enterpriseService.update(enterprise);
    return new Result(true,StatusCode.OK,"修改成功");
  }

  @DeleteMapping("/{enterpriseId}")
  public Result deleteById(@PathVariable("enterpriseId") String id){
    enterpriseService.deleteById(id);
    return new Result(true,StatusCode.OK,"删除成功");
  }

  @RequestMapping(value = "/search/{page}/{size}", method=RequestMethod.POST)
  public Result findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size){
    Page<Enterprise> pageList = enterpriseService.findSearch(searchMap,page,size);
    return new Result(true,StatusCode.OK,"查询成功",new PageResult<Enterprise>(pageList.getTotalElements(),pageList.getContent()));
  }
  @RequestMapping(value = "/search",method = RequestMethod.POST)
  public Result findSearch(@RequestBody Map searchMap){
    return new Result(true,StatusCode.OK,"查询成功",enterpriseService.findSearch(searchMap));
  }
  @RequestMapping(method=RequestMethod.POST)
  public Result add(@RequestBody Enterprise enterprise  ){
    enterpriseService.add(enterprise);
    return new Result(true,StatusCode.OK,"增加成功");
  }

  /**
   * 修改
   * @param enterprise
   */
  @RequestMapping(value="/{id}",method= RequestMethod.PUT)
  public Result update(@RequestBody Enterprise enterprise, @PathVariable String id ){
    enterprise.setId(id);
    enterpriseService.update(enterprise);
    return new Result(true,StatusCode.OK,"修改成功");
  }

  /**
   * 删除
   * @param id
   */
  @RequestMapping(value="/{id}",method= RequestMethod.DELETE)
  public Result delete(@PathVariable String id ){
    enterpriseService.deleteById(id);
    return new Result(true,StatusCode.OK,"删除成功");
  }


}
