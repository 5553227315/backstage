package com.example.filmback.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 *
 * </p>
 *
 * @author Wyz
 * @since 2022-05-06
 */
@Getter
@Setter
@TableName("want_watch")
@ApiModel(value = "Want对象", description = "")
public class Want implements Serializable {


    //将Long转成string
    @JsonSerialize(using = ToStringSerializer.class)
    //“type”：设置主键类型、如果数据库主键设置了自增建议使用“AUTO”(value="",type=Idtype.AUTO)
    @TableId(value = "USER_ID")
    @ApiModelProperty("用户ID")
    private Long userId;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("电影ID")
    private Long filmId;

    @ApiModelProperty("想看")
    private Integer isWant;


}
