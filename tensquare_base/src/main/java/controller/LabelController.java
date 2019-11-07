package controller;

import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pojo.Label;
import service.LabelService;

import java.util.List;

@RestController
@RequestMapping("/label")
public class LabelController {

  @Autowired
  private LabelService labelService;

  @DeleteMapping("/{labelId}")
  public Result deleteById(@PathVariable("labelId") String id) {
    labelService.deleteById(id);
    return new Result(true, StatusCode.OK, "删除成功");

  }

  @GetMapping
  public Result findAll() {
    return new Result(true, StatusCode.OK, "查询成功", labelService.findAll());
  }

  @GetMapping("/{labelId}")
  public Result findById(@PathVariable("labelId") String Id) {
    return new Result(true, StatusCode.OK, "查询成功", labelService.findById(Id));
  }

  @PostMapping
  public Result save(@RequestBody Label label) {
    return new Result(true, StatusCode.OK, "添加成功");
  }

  @PutMapping("{lableId}")
  public Result update(@PathVariable("lableId") String id, @RequestBody Label label) {
//    label.setId(id);
    labelService.update(label);
    return new Result(true, StatusCode.OK, "修改成功");
  }


//  @PostMapping("/search")
//  public Result findSearch(@RequestBody Label label){
//    List<Label> labels = labelService.findSearch(label);
//  }
}
