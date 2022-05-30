package com.example.filmback.config.interceptor;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.filmback.common.Constants;
import com.example.filmback.controller.dto.AdminDTO;
import com.example.filmback.exception.ServiceException;
import com.example.filmback.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @title: token拦截器
 * @Autor: wyz
 */
public class  JwtInterceptor implements HandlerInterceptor {
    @Autowired
    private IAdminService adminService;
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object headler){
        String token = request.getHeader("token");

        // 如果不是映射到方法直接通过
        if (!(headler instanceof HandlerMethod)){
            return true;
        }
        //执行认证
        if (StrUtil.isBlank(token)){
            throw new ServiceException(Constants.CODE_401,"无token，请重新登陆");
        }
        // 获取 token 中的 user账号
        String admin;
        try {
            admin = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            throw new ServiceException(Constants.CODE_401,"token验证失败，请重新登陆");
        }
        //通过账号去查询
        QueryWrapper<AdminDTO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ADMIN", admin);
        AdminDTO adminDTO = adminService.list(queryWrapper).get(0);
        if (adminDTO == null) {
            throw new ServiceException(Constants.CODE_404,"用户不存在，请重新登录");
        }
        // 用户密码加签验证 token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(adminDTO.getAdminPassword())).build();
        try {
            jwtVerifier.verify(token);//验证token
        } catch (JWTVerificationException e) {
            throw new ServiceException(Constants.CODE_401,"token验证失败,请重新登陆");
        }
        return true;
    }
}
