package com.example.filmback.entity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

//获取数据方法
@Data
//指定哪个数据库
@TableName(value = "user_info")
public class User {
    //将Long转成string
    @JsonSerialize(using= ToStringSerializer.class)
    //“type”：设置主键类型、如果数据库主键设置了自增建议使用“AUTO”(value="",type=Idtype.AUTO)
    @TableId(value = "USER_ID")
    private Long userId;
    private String sex;
    private String birthday;
    private String userCity;
    private String tel;
    private String userAddress;
    //    不展示密码
//    @JsonIgnore
    private String userPassword;
    private String userName;
    private String createTime;
    //指定数据库中列名称,与主键id类似
    @TableField(value = "AVATAR_URL")
    private String avatar;


}
