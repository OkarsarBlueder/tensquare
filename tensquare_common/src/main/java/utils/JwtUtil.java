package utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;

import java.util.Date;

@Data
public class JwtUtil {

  private String key;
  private Long ttl;//one hour

  /**
   * 生成JWT
   *
   * @param id
   * @param subject
   * @return
   */
  public String createJWT(String id, String subject, String roles) {
    long nowMillis = System.currentTimeMillis();
    Date now = new Date(nowMillis);
    JwtBuilder jwtBuilder = Jwts.builder().setId(id)
        .setSubject(subject)
        .setIssuedAt(now)
        .signWith(SignatureAlgorithm.HS256, key)
        .claim("roles", roles);
    if (ttl > 0)
      jwtBuilder.setExpiration(new Date(nowMillis + ttl));
    return jwtBuilder.compact();
  }

  /**
   * 解析JWT
   * @param jwtStr
   * @return
   */
  public Claims parseJWt(String jwtStr){
    return Jwts.parser()
        .setSigningKey(key)
        .parseClaimsJws(jwtStr)
        .getBody();
  }


}
