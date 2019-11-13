package com.tensquare.qa.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name="tb_problem")
public class Problem implements Serializable {
  @Id
  private String id;
  private String title;
  private String content;
  private Date createtime;
  private Date updatetime;
  private String userid;
  private String nickname;
  private Long visits;
  private Long thumbup;
  private Long reply;
  private String solve;
  private String replyname;
  private Date replytime;
}
