package com.tensquare.user.pojo;

import javax.persistence.Id;
import java.io.Serializable;

public class Admin implements Serializable {

  @Id
  private String id;//ID
  private String loginname;//登陆名称
  private String password;//密码
  private String state;//状态

}
