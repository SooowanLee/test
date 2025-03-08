package com.example.member.config;

import com.example.member.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration //설정정보를 스프링 실행 시 등록해줌
public class WebConfig implements WebMvcConfigurer {

    //로그인 여부에 따른 접속 가능 페이지 구분
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //본인이 만든 Interceptor등록
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1) //해당 인터셉터가 적용되는 순서, 1이면 첫번째로 실행
                .addPathPatterns("/**") //현재 프로젝트의 모든 주소에 대해 인터셉터를 적용
                .excludePathPatterns("/", "/member/save", "/member/login", "/member/logout", "/css/**");
                //제외할 주소
    }
}
