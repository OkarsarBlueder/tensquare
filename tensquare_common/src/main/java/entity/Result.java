package entity;

import lombok.Data;

@Data
public class Result {

  private Integer code;
  private Object data;
  private boolean flag;// 是否成功
  private String message;

  public Result(boolean flag, Integer code, String message, Object data) {
    this.flag = flag;
    this.code = code;
    this.message = message;
    this.data = data;
  }


  public Result(boolean flag, Integer code, String message) {
    this.flag = flag;
    this.code = code;
    this.message = message;
  }


  public Result() {
  }


}
