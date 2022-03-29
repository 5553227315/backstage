package com.example.filmback.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.filmback.entity.User;
import com.example.filmback.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @PostMapping
    public boolean save(@RequestBody User user){
        //新增或者更新
        return userService.saveUser(user);
    }

    //用户名查重
    @GetMapping("/{tel}")
    public Long findTel(@RequestParam String tel){

        QueryWrapper<User> queryWrapper = new  QueryWrapper<>();
        queryWrapper.eq("TEL",tel);
        return userMapper.selectCount(queryWrapper);

    }

    //查询所有
    @GetMapping
    public List<User> findall(){
        return userService.list();
    }

    //删除
    @DeleteMapping("/{userId}")
    public boolean delete(@PathVariable Long userId){
        return userService.removeById(userId);
    }

    //分页查询
    //接口路径  /user/page?pageNum=1&pageSize=10
    //@RequestParam 接收  ?pageNum=1&pageSize=10
    //limit 第一个参数=(pageNum-1)*pageSize,第二个参数pageSize
//    @GetMapping("/page")
//    public Map<String,Object> findPage(@RequestParam Integer pageNum,
//                                       @RequestParam Integer pageSize,
//                                       @RequestParam String userName,
//                                       @RequestParam String userAddress,
//                                       @RequestParam String tel){
//        pageNum=(pageNum-1)*pageSize;
//        userName = "%"+userName+"%";
//        userAddress = "%"+userAddress+"%";
//        tel = "%"+tel+"%";
//        List<User> data = userMapper.selectPage(pageNum,pageSize,userName,userAddress,tel);
//        Integer total = userMapper.selectTotal(userName,userAddress,tel);
//        Map<String,Object> res = new HashMap<>();
//        res.put("data",data);
//        res.put("total",total);
//        return res;
//
//    }
    //分页查询 mybatis-plus方法
    @GetMapping("/page")
    public IPage<User> findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize,
                                //没有也可以查询
                                @RequestParam(defaultValue = "") String userName,
                                @RequestParam(defaultValue = "") String userAddress,
                                @RequestParam(defaultValue = "") String tel){
        IPage<User> page = new Page<>(pageNum,pageSize);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if(!"".equals(userName)){
            queryWrapper.like("USER_NAME",userName);
        }
        if (!"".equals(userAddress)){
            queryWrapper.like("USER_ADDRESS",userAddress);
        }
        if (!"".equals(tel)){
            queryWrapper.like("TEL",tel);
        }
        //通过时间倒序
        queryWrapper.orderByDesc("CREATE_TIME");
        return userService.page(page,queryWrapper);

    }
}
