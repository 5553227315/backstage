package com.example.filmback.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.filmback.entity.User;
import org.apache.ibatis.annotations.*;


import java.util.List;

//@Mapper
public interface UserMapper extends BaseMapper<User> {

//    @Select("SELECT USER_ID userId,SEX sex,BIRTHDAY birthday,USER_CITY userCity,TEL tel,USER_ADDRESS userAddress," +
//            "USER_PASSWORD userPassword,USER_NAME userName,CREATE_TIME createTime from user_info")
//    List<User> findAll();
//
//    @Insert("INSERT INTO user_info(USER_ID,SEX,BIRTHDAY,USER_CITY,TEL,USER_ADDRESS,USER_PASSWORD,USER_NAME) " +
//            "VALUES(#{userId},#{sex},#{birthday},#{userCity},#{tel},#{userAddress},#{userPassword},#{userName})")
//    int insert(User user);
//
//    int update(User user);
//
//    @Delete("delete from user_info where USER_ID = #{userId}")
//    Integer deleteByUserId(@Param("userId") Long userId);
//
//    @Select("select USER_ID userId,SEX sex,BIRTHDAY birthday,USER_CITY userCity,TEL tel,USER_ADDRESS userAddress," +
//            "USER_PASSWORD userPassword,USER_NAME userName,CREATE_TIME createTime from user_info where USER_NAME" +
//            " like #{userName} and USER_ADDRESS like #{userAddress} and TEL like #{tel} limit #{pageNum}, #{pageSize}")
//    List<User> selectPage(Integer pageNum, Integer pageSize, String userName, String userAddress, String tel);
//
//    @Select("select count(*) from user_info where USER_NAME like #{userName} and USER_ADDRESS like #{userAddress} and TEL like #{tel}")
//    Integer selectTotal(String userName, String userAddress, String tel);

}
