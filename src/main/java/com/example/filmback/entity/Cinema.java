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
 * 影院信息表
 * </p>
 *
 * @author Wyz
 * @since 2022-03-24
 */
@Getter
@Setter
@TableName("cinema_info")
@ApiModel(value = "Cinema对象", description = "影院信息表")
public class Cinema implements Serializable {

    private static final long serialVersionUID = 1L;

    //将Long转成string
    @JsonSerialize(using = ToStringSerializer.class)
    //“type”：设置主键类型、如果数据库主键设置了自增建议使用“AUTO”(value="",type=Idtype.AUTO)
    @TableId(value = "Cinema_ID")
    @ApiModelProperty("影院ID")
    private Long cinemaId;

    @ApiModelProperty("影院名")
    private String cinemaName;

    @ApiModelProperty("地址")
    private String cinemaAddress;

    @ApiModelProperty("最低价格")
    private String bottomPrice;

    @ApiModelProperty("影厅类型：IMAX厅、CGS中国巨幕厅、Dolby Cinema厅、ReaID厅、4K厅、ReaID 6FL厅、LUXE巨幕厅、4DX厅、DTS:X临境音厅、4D厅、巨幕厅")
    private String cinemaType;

    @ApiModelProperty("影院坐标")
    private String cinemaLocation;

    @ApiModelProperty("影院电话")
    private String cinemaTel;

    @ApiModelProperty("创建时间")
    private String createCinema;


}
