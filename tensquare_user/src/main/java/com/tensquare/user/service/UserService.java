package com.tensquare.user.service;

import com.tensquare.user.dao.UserDao;
import com.tensquare.user.pojo.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import utils.IdWorker;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Service
public class UserService {
  @Resource
  private UserDao userDao;

  @Resource
  private IdWorker idWorker;

  @Resource
  private RedisTemplate redisTemplate;

  @Resource
  private RabbitTemplate rabbitTemplate;

  @Resource
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  private HttpServletRequest request;

  /**
   * 更新被关注好友粉丝数跟用户自己的关注数
   * @param num
   * @param userId
   * @param friendId
   */
  public void updateFansAndFollower(int num,String userId,String friendId){
    userDao.updateFansNum(num,friendId);
    userDao.updateFollowNum(num,userId);
  }

  /**
   * 用户登录
   * @param mobile
   * @param password
   * @return
   */
  public User login(String mobile, String password) {
    User user = userDao.findByMobile(mobile);
    if(user!= null && bCryptPasswordEncoder.matches(password,user.getPassword())){
      return user;
    }
    return null;
  }

  /**
   * 发送短信
   *
   * @param mobile
   */
  public void sendSms(String mobile) {
    String checkCode = RandomStringUtils.randomNumeric(6);
    redisTemplate.opsForValue().set("")
  }
}
