package com.example.filmback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.filmback.controller.dto.AdminDTO;
import com.example.filmback.entity.ReturnInfo;
import com.example.filmback.mapper.AdminMapper;
import com.example.filmback.service.IAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
    public ReturnInfo login(String admin, String adminPassword) {
        QueryWrapper<AdminDTO> queryWrapper = new QueryWrapper<>();
        ReturnInfo returnInfo = new ReturnInfo();
        List<AdminDTO> one = list(queryWrapper);
        if (!"".equals(admin)) {
            queryWrapper.eq("ADMIN", admin);
        }

        if (one.size()==0){
            returnInfo.setMessage("用户名不存在");
            returnInfo.setRetCode(200);

        }else {
            if (Objects.equals(adminPassword, one.get(0).getAdminPassword())){
                returnInfo.setMessage("登录成功");
                returnInfo.setRetCode(500);
                returnInfo.setAdminName(one.get(0).getAdminName());
            }else{
                returnInfo.setMessage("密码不正确");;
                returnInfo.setRetCode(201);
            }
        }
        return returnInfo;
    }
}
