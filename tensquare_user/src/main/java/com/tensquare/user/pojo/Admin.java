package com.tensquare.user.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "tb_user")
public class Admin implements Serializable {

  @Id
  private String id;//ID
  private String loginname;//登陆名称
  private String password;//密码
  private String state;//状态

}
