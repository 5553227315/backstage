package com.example.filmback.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.filmback.entity.SnowflakeIdWorker;
import com.example.filmback.entity.User;
import com.example.filmback.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Service
public class UserService extends ServiceImpl<UserMapper, User>{
    public boolean saveUser(User user) {
//        if(user.getUserId() == null){
//            return save(user);  //mybatis-plus提供 表示插入
//        }else {
//            return updateById(user);
//        }
        //修改时间长度
        user.setBirthday(user.getBirthday().substring(0,10));
        System.out.println(user.getBirthday());
        //判定新增还是更新
        if (user.getUserId() == null) {
            //生成唯一ID
            SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
            Long id = idWorker.nextId();
            user.setUserId(id);
        }
        return saveOrUpdate(user);
    }

}
