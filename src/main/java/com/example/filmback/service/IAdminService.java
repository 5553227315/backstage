package com.example.filmback.service;

import com.example.filmback.controller.dto.AdminDTO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.filmback.entity.ReturnInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Wyz
 * @since 2022-03-28
 */
public interface IAdminService extends IService<AdminDTO> {


    ReturnInfo login(String admin, String adminPassword);
}
