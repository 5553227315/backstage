package com.example.filmback.controller;

import com.example.filmback.common.Result;
import com.example.filmback.service.impl.AdminServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import com.example.filmback.service.IAdminService;
import com.example.filmback.controller.dto.AdminDTO;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Wyz
 * @since 2022-03-28
 */
@RestController
@RequestMapping("/admin")
public class LoginController {
    @Resource
    private AdminServiceImpl adminService;

    @PostMapping("/login")
    public Result login(@RequestBody AdminDTO adminDTO){
        String admin = adminDTO.getAdmin();
        String adminPassword = adminDTO.getAdminPassword();
        return adminService.login(admin,adminPassword);

    }
}

