package com.example.filmback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.filmback.common.Constants;
import com.example.filmback.common.Result;
import com.example.filmback.controller.dto.AdminDTO;
import com.example.filmback.exception.ServiceException;
import com.example.filmback.mapper.AdminMapper;
import com.example.filmback.service.IAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.filmback.utils.TokenUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Wyz
 * @since 2022-03-28
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, AdminDTO> implements IAdminService {

    @Override
    public Result login(String admin, String adminPassword) {
        QueryWrapper<AdminDTO> queryWrapper = new QueryWrapper<>();

        if (!"".equals(admin)) {
            queryWrapper.eq("ADMIN", admin);
        }
        List<AdminDTO> one = list(queryWrapper);
        if (one.size()==0){
            throw new  ServiceException(Constants.CODE_404,"用户不存在");

        }else {
            String token = TokenUtils.getToken(admin,adminPassword);
            one.get(0).setToken(token);
            System.out.println(one.get(0));
            if (Objects.equals(adminPassword, one.get(0).getAdminPassword())){
                return Result.success("登录成功！"+one.get(0).getAdminName(),one.get(0));
            }else{

                throw new ServiceException(Constants.CODE_401,"密码不正确");
            }
        }

    }

}
