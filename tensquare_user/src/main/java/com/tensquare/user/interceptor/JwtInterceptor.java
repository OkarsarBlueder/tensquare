package com.tensquare.user.interceptor;

import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import utils.JwtUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {

  @Resource
  private JwtUtil jwtUtil;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
    String header = request.getHeader("Authorization");
    if (!StringUtils.isEmpty(header)) {
      if (header.startsWith("Bearer")) {
        final String token = header.substring(7);
        try {
          Claims claims = jwtUtil.parseJWt(token);
          String roles = (String) claims.get("roles");
          if ("admin".equals(roles)) {
            request.setAttribute("claims_user", token);
          }
          if ("user".equals(roles)) {
            request.setAttribute("claims_user", token);
          }
        } catch (Exception e) {
          throw new RuntimeException("令牌有误");
        }
      }
    }
    return true;
  }


}
