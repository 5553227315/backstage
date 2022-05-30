package com.example.filmback.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author Wyz
 * @since 2022-03-23
 */
@Getter
@Setter
@TableName("user_info")
@ApiModel(value = "User对象", description = "用户信息表")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;


    //将Long转成string
    @JsonSerialize(using = ToStringSerializer.class)
    //“type”：设置主键类型、如果数据库主键设置了自增建议使用“AUTO”(value="",type=Idtype.AUTO)
    @TableId(value = "USER_ID")
    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("性别：1.男、2.女")
    private String userGender;

    @ApiModelProperty("用户生日")
    private String birthday;


    @ApiModelProperty("手机号码")
    private String userTel;

    @ApiModelProperty("最近登录地址")
    private String userAddress;

    @ApiModelProperty("用户密码")
    private String userPassword;

    @ApiModelProperty("创建时间")
    private String createTime;

    @ApiModelProperty("头像")
    private String avatarUrl;


}
