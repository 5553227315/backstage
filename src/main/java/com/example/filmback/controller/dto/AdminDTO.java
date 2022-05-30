package com.example.filmback.controller.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author Wyz
 * @since 2022-03-28
 */
@Data
@TableName("admin_info")
public class AdminDTO implements Serializable {


    //将Long转成string
    @JsonSerialize(using = ToStringSerializer.class)
    //“type”：设置主键类型、如果数据库主键设置了自增建议使用“AUTO”(value="",type=Idtype.AUTO)
    @TableId(value = "ADMIN")
    @ApiModelProperty("管理员账号")
    private String admin;

    @ApiModelProperty("密码")
    @TableField(value = "ADMIN_PASSWORD")
    private String adminPassword;

    @ApiModelProperty("管理员姓名")
    @TableField(value = "ADMIN_NAME")
    private String adminName;

    //排除数据库查询次属性
    @TableField(exist = false)
    private String token;

}
