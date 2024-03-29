package com.tensquare.user.dao;

import com.tensquare.user.pojo.User;
import entity.PageResult;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserDao extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

  Page<User> findAll(Specification<User> specification, PageResult pageResult);


  User findByMobile(String mobile);


  @Modifying
  @Query(value = "UPDATE tb_user SET fanscount = fanscount + ? WHERE id = ?", nativeQuery = true)
  void updateFansNum(int num, String friendId);


  @Modifying
  @Query(value = "UPDATE tb_user SET followcount = followcount + ? WHERE id = ?", nativeQuery = true)
  void updateFollowNum(int num, String userId);


}
