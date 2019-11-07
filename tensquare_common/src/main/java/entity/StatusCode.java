package entity;

import lombok.Getter;

@Getter
public class StatusCode {

  public static final int ACCESSERROR = 20004;
  public static final int ERROR = 20001;
  public static final int LOGINERROR = 20002;
  public static final int OK = 20000;
  public static final int REPERROR = 20005;

}
