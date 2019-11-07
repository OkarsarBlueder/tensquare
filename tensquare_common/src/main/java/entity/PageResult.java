package entity;

import lombok.Data;

import java.util.List;

@Data
public class PageResult<T> {

  private List<T> rows;
  private Long total;

  public PageResult(Long total, List<T> rows) {
    super();
    this.total = total;
    this.rows = rows;
  }


  public PageResult() {
  }


}
