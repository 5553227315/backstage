package com.example.filmback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.filmback.common.Constants;
import com.example.filmback.entity.User;
import com.example.filmback.exception.ServiceException;
import com.example.filmback.mapper.UserMapper;
import com.example.filmback.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author Wyz
 * @since 2022-03-23
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    public User login(String userTel){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("USER_TEL", userTel);
        List<User> user = list(queryWrapper);
        if (user.size()==0){
            throw new ServiceException(Constants.CODE_201,"账号不存在");
        }else {
            return user.get(0);
        }

    }
}
