package com.example.member.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {
    final String LOGIN_EMAIL = "loginEmail";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //사용자가 요청한 URI
        String requestURI = request.getRequestURI();
        log.info("requestURI = {}", requestURI);

        HttpSession session = request.getSession();

        if (session.getAttribute(LOGIN_EMAIL) == null) {
            //미 로그인 상태
            //비회원은 로그인 페이지로 회원은 요청한 페이지로 보낸다
            session.setAttribute("redirectURL", requestURI);
            response.sendRedirect("/member/login");
            return false;
        } else {
            // 로그인 상태
            return true;
        }
    }
}
