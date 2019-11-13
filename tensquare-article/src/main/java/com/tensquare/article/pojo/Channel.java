package com.tensquare.article.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "tb_channel")
public class Channel {

  @Id
  private String id;//ID
  private String name;//频道名称
  private String state;//状态

}
