package com.example.filmback.controller;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.filmback.entity.SnowflakeIdWorker;
import org.apache.catalina.filters.ExpiresFilter;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;

import com.example.filmback.service.IUserService;
import com.example.filmback.entity.User;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author Wyz
 * @since 2022-03-23
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private IUserService userService;

    //新增或者更新
    @PostMapping
    public boolean save(@RequestBody User user) {

        //判定新增还是更新
        if (user.getUserId() == null) {
            //生成唯一ID
            SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
            Long id = idWorker.nextId();
            user.setUserId(id);
        }
        return userService.saveOrUpdate(user);
    }

    //删除
    @DeleteMapping("/{userId}")
    public boolean delete(@PathVariable Long userId) {
        return userService.removeById(userId);
    }

    //查询所有
    @GetMapping
    public List<User> findall() {
        return userService.list();
    }

    //分页查询 mybatis-plus方法
    @GetMapping("/userPage")
    public Page<User> findPage(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize,
//                                没有也可以查询
                               @RequestParam(defaultValue = "") String userName,
                               @RequestParam(defaultValue = "") String userAddress,
                               @RequestParam(defaultValue = "") String userTel) {
        Page<User> page = new Page<>(pageNum, pageSize);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (!"".equals(userName)) {
            queryWrapper.like("USER_NAME", userName);
        }
        if (!"".equals(userAddress)) {
            queryWrapper.like("USER_ADDRESS", userAddress);
        }
        if (!"".equals(userTel)) {
            queryWrapper.like("USER_TEL", userTel);
        }

//        通过时间倒序
        queryWrapper.orderByDesc("CREATE_TIME");
        return userService.page(page, queryWrapper);
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {

        //从数据库查询所有的数据
        List<User> list = userService.list();
        //通过工具类创建writer 写出磁盘路径
//        ExcelWriter writer = Exception.getWriter(filesUploadPath + "/用户信息.xlsx");

        //在内存操作写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);

        //自定义别名
        writer.addHeaderAlias("userId","USER_ID");
        writer.addHeaderAlias("userName", "姓名");
        writer.addHeaderAlias("sex", "性别");
        writer.addHeaderAlias("birthday", "生日");
        writer.addHeaderAlias("userCity", "用户所在城市");
        writer.addHeaderAlias("userAddress", "最近登录地址");
        writer.addHeaderAlias("userTel", "账号");
        writer.addHeaderAlias("userPassword", "用户密码");
        writer.addHeaderAlias("createTime", "创建时间");

        //一次性写出list对象到Excel，使用默认样式，强制输出标题
        writer.write(list, true);

        //设置浏览器响应格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("用户信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();
    }
}







