package com.tensquare.user.service;

import com.tensquare.user.dao.UserDao;
import com.tensquare.user.pojo.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import utils.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;
  @Autowired
  private IdWorker idWorker;
  @Autowired
  private RabbitTemplate rabbitTemplate;
  @Autowired
  private RedisTemplate redisTemplate;
  @Autowired
  private HttpServletRequest request;
  @Autowired
  private UserDao userDao;

  public void add(User user) {
    user.setId(idWorker.nextId() + "");
    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    user.setFollowcount(0);
    user.setFanscount(0);
    user.setOnline(0L);
    user.setRegdate(new Date());
    user.setUpdatedate(new Date());
    user.setLastdate(new Date());
    userDao.save(user);

  }


  public void deleteById(String id) {
    String token = (String) request.getAttribute("claims_admin");
    if (StringUtils.isEmpty(token)) {
      throw new RuntimeException("权限不足！");
    }
    userDao.deleteById(id);
  }


  public List<User> findAll() {
    return userDao.findAll();
  }


  public User findById(String id) {
    return userDao.findById(id).get();
  }


  public Page<User> findSearch(Map whereMap, int page, int size) {
    Specification<User> specification = createSpecification(whereMap);
    PageRequest pageRequest = PageRequest.of(page - 1, size);
    return userDao.findAll(specification, pageRequest);
  }


  private Specification<User> createSpecification(Map searchMap) {
    return new

        Specification<User>() {

          List<Predicate> predicateList = new ArrayList();

          @Override
          public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
            // ID
            if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
              predicateList.add(cb.like(root.get("id").as(String.class), "%" + (String) searchMap.get("id") + "%"));
            }
            // 手机号码
            if (searchMap.get("mobile") != null && !"".equals(searchMap.get("mobile"))) {
              predicateList.add(cb.like(root.get("mobile").as(String.class), "%" + (String) searchMap.get("mobile") + "%"));
            }
            // 密码
            if (searchMap.get("password") != null && !"".equals(searchMap.get("password"))) {
              predicateList.add(cb.like(root.get("password").as(String.class), "%" + (String) searchMap.get("password") + "%"));
            }
            // 昵称
            if (searchMap.get("nickname") != null && !"".equals(searchMap.get("nickname"))) {
              predicateList.add(cb.like(root.get("nickname").as(String.class), "%" + (String) searchMap.get("nickname") + "%"));
            }
            // 性别
            if (searchMap.get("sex") != null && !"".equals(searchMap.get("sex"))) {
              predicateList.add(cb.like(root.get("sex").as(String.class), "%" + (String) searchMap.get("sex") + "%"));
            }
            // 头像
            if (searchMap.get("avatar") != null && !"".equals(searchMap.get("avatar"))) {
              predicateList.add(cb.like(root.get("avatar").as(String.class), "%" + (String) searchMap.get("avatar") + "%"));
            }
            // E-Mail
            if (searchMap.get("email") != null && !"".equals(searchMap.get("email"))) {
              predicateList.add(cb.like(root.get("email").as(String.class), "%" + (String) searchMap.get("email") + "%"));
            }
            // 兴趣
            if (searchMap.get("interest") != null && !"".equals(searchMap.get("interest"))) {
              predicateList.add(cb.like(root.get("interest").as(String.class), "%" + (String) searchMap.get("interest") + "%"));
            }
            // 个性
            if (searchMap.get("personality") != null && !"".equals(searchMap.get("personality"))) {
              predicateList.add(cb.like(root.get("personality").as(String.class), "%" + (String) searchMap.get("personality") + "%"));
            }
            return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
          }


        };
  }


  public List<User> findSearch(Map whereMap) {
    Specification<User> specification = createSpecification(whereMap);
    return userDao.findAll(specification);
  }


  /**
   * 用户登录
   *
   * @param mobile
   * @param password
   * @return
   */
  public User login(String mobile, String password) {
    User user = userDao.findByMobile(mobile);
    if (user != null && bCryptPasswordEncoder.matches(password, user.getPassword())) {
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
    redisTemplate.opsForValue().set("check_code_" + mobile, checkCode, 6, TimeUnit.HOURS);
    Map<String, String> map = new HashMap<>();
    map.put("mobile", mobile);
    map.put("check_code", checkCode);
    rabbitTemplate.convertAndSend("sms", map);
    System.out.println("验证码为：" + checkCode);

  }


  public void update(User user) {
    userDao.save(user);
  }


  /**
   * 更新被关注好友粉丝数跟用户自己的关注数
   *
   * @param num
   * @param userId
   * @param friendId
   */
  public void updateFansAndFollower(int num, String userId, String friendId) {
    userDao.updateFansNum(num, friendId);
    userDao.updateFollowNum(num, userId);
  }


}
