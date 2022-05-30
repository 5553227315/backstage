package com.example.filmback.config;

import com.example.filmback.config.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())
                .addPathPatterns("/**")  // 拦截所有请求，通过判断 token决定是否合法需要登录
                .excludePathPatterns("/admin/login", "/user/login","/user/save","/user/userTel","/user/checkTel")
                .excludePathPatterns("/files/upload", "/**/export", "/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**", "/api", "/api-docs", "/api-docs/**")
                .excludePathPatterns( "/**/*.html", "/**/*.js", "/**/*.jpg","/**/*.png","/**/*.jpeg", "/**/*.JPG","/**/*.PNG", "/**/*.css", "/**/*.woff", "/**/*.ttf")// 放行静态文件
                .excludePathPatterns("/Swiper","/film","/film/canShow","/film/cantShow","/film/filmId","/film/searchFilm","/film/eve","/film/findevaluate")
                .excludePathPatterns("/cinema","/cinema/findall","/cinema/searchCinema","/cinema/cinemaId","/cinema/cinemaLocation")
                .excludePathPatterns("/showings","/showings/filmShowing","/showings/timeShowing","/showings/filmInfo","/showings/hall","/showings/evaluate")
                .excludePathPatterns("/hall/saveall","/seat/setSeat","/seat-showings/showingsId","/seat-showings/setSeat","/seat-showings/seatState")
                .excludePathPatterns("/bill","/bill/userBill","/bill/deleBill","/bill/reticket")
                .excludePathPatterns("/evaluate","/evaluate/haveEvaluate","/evaluate/filmEva")
                .excludePathPatterns("/want","/want/deleWant","/want/findWant","/want/saveWant","/want/wangSize","/want/suaveeee")


        ;

    }


    @Bean
    public JwtInterceptor jwtInterceptor(){
        return new JwtInterceptor();

    }
}
