package com.tensquare.user.controller;

import com.tensquare.user.pojo.User;
import com.tensquare.user.service.UserService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import utils.JwtUtil;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  private JwtUtil jwtUtil;
  @Autowired
  private RedisTemplate redisTemplate;
  @Autowired
  private UserService userService;

  /**
   * 增加
   *
   * @param user
   */
  @RequestMapping(method = RequestMethod.POST)
  public Result add(@RequestBody User user) {
    userService.add(user);
    return new Result(true, StatusCode.OK, "增加成功");
  }

  /**
   * 删除
   *
   * @param id
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public Result delete(@PathVariable String id) {
    userService.deleteById(id);
    return new Result(true, StatusCode.OK, "删除成功");
  }

  @GetMapping
  public Result findAll() {
    return new Result(true, StatusCode.OK, "查询成功", userService.findAll());
  }

  @GetMapping("/{id}")
  public Result findById(@PathVariable String id) {
    return new Result(true, StatusCode.OK, "查询过成功", userService.findById(id));
  }

  @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
  public Result findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
    Page<User> pageList = userService.findSearch(searchMap, page, size);
    return new Result(true, StatusCode.OK, "查询成功", new PageResult<User>(pageList.getTotalElements(), pageList.getContent()));
  }

  /**
   * 根据条件查询
   *
   * @param searchMap
   * @return
   */
  @RequestMapping(value = "/search", method = RequestMethod.POST)
  public Result findSearch(@RequestBody Map searchMap) {
    return new Result(true, StatusCode.OK, "查询成功", userService.findSearch(searchMap));
  }

  @PostMapping("/login")
  public Result login(@RequestBody User user) {
    user = userService.login(user.getMobile(), user.getPassword());
    if (user == null) {
      return new Result(false, StatusCode.LOGINERROR, "登录失败");
    }
    String token = jwtUtil.createJWT(user.getId(), user.getMobile(), "user");
    Map<String, Object> map = new HashMap<>();
    map.put("token", token);
    map.put("roles", "user");
    return new Result(true, StatusCode.OK, "登录成功", map);
  }

  @PostMapping("/register/{code}")
  public Result register(@PathVariable String code, @RequestBody User user) {
    String checkCode = (String) redisTemplate.opsForValue().get("check_code_" + user.getMobile());
    if (StringUtils.isEmpty(checkCode)) {
      return new Result(false, StatusCode.ERROR, "请先获取验证码");
    }
    if (!checkCode.equals(code)) {
      return new Result(false, StatusCode.ERROR, "请输入正确的验证码");
    }
    userService.add(user);
    return new Result(true, StatusCode.OK, "注册成功");
  }

  @PostMapping("/sendsms/{mobile}")
  public Result sendSms(@PathVariable String mobile) {
    userService.sendSms(mobile);
    return new Result(true, StatusCode.OK, "发送成功");
  }

  /**
   * 修改
   *
   * @param user
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
  public Result update(@RequestBody User user, @PathVariable String id) {
    user.setId(id);
    userService.update(user);
    return new Result(true, StatusCode.OK, "修改成功");
  }

  @PutMapping("/{userId}/{friend}/{num}")
  public void updateFansAndFollower(@PathVariable String userId,
                                    @PathVariable String friend,
                                    @PathVariable int num) {
    userService.updateFansAndFollower(num, userId, friend);
  }


}
