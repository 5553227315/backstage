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
 * @since 2022-05-02
 */
@Getter
@Setter
@TableName("swiper_info")
@ApiModel(value = "swiper对象", description = "")
public class Swiper implements Serializable {

    //将Long转成string
    @JsonSerialize(using = ToStringSerializer.class)
    //“type”：设置主键类型、如果数据库主键设置了自增建议使用“AUTO”(value="",type=Idtype.AUTO)
    @TableId(value = "FILES_ID")
    @ApiModelProperty("轮播图ID")
    private Long filesId;

    @ApiModelProperty("排第几位")
    private Integer swiperRank;

    @ApiModelProperty("下载连接")
    private String filesUrl;


}
