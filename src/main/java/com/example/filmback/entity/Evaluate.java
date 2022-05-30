package com.example.filmback.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 用户评价表
 * </p>
 *
 * @author Wyz
 * @since 2022-03-24
 */
@Getter
@Setter
@TableName("user_evaluate")
@ApiModel(value = "Evaluate对象", description = "用户评价表")
public class Evaluate implements Serializable {


    //将Long转成string
    @JsonSerialize(using = ToStringSerializer.class)
    //“type”：设置主键类型、如果数据库主键设置了自增建议使用“AUTO”(value="",type=Idtype.AUTO)
    @TableId(value = "EVALUATE_ID")
    private Long evaluateId;

    //将Long转成string
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("用户ID")
    private Long userId;

    //将Long转成string
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("订单ID")
    private Long billId;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("用户账号")
    private String userTel;

    @ApiModelProperty("电影名")
    private String filmName;

    //将Long转成string
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("电影ID")
    private Long filmId;

    @ApiModelProperty("电影评分")
    private String filmScore;

    @ApiModelProperty("用户评价")
    private String userEvaluate;

    @ApiModelProperty("创建时间")
    private String createEvaluate;

    @TableField(exist = false)
    private String avatarUrl;
}
