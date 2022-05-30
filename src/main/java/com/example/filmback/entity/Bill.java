package com.example.filmback.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.example.filmback.common.Result;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 订单信息表
 * </p>
 *
 * @author Wyz
 * @since 2022-03-27
 */
@Getter
@Setter
@TableName("bill_info")
@ApiModel(value = "Bill对象", description = "订单信息表")
public class Bill implements Serializable {

    private static final long serialVersionUID = 1L;

    //将Long转成string
    @JsonSerialize(using = ToStringSerializer.class)
    //“type”：设置主键类型、如果数据库主键设置了自增建议使用“AUTO”(value="",type=Idtype.AUTO)
    @TableId(value = "BILL_ID")
    @ApiModelProperty("订单ID")
    private Long billId;

    //将Long转成string
    @JsonSerialize(using = ToStringSerializer.class)
    //“type”：设置主键类型、如果数据库主键设置了自增建议使用“AUTO”(value="",type=Idtype.AUTO)
    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("用户名称")
    private String userName;

    @ApiModelProperty("手机号码")
    private String userTel;

    //将Long转成string
    @JsonSerialize(using = ToStringSerializer.class)
//“type”：设置主键类型、如果数据库主键设置了自增建议使用“AUTO”(value="",type=Idtype.AUTO)
    @ApiModelProperty("电影ID")
    private Long filmId;

    @ApiModelProperty("电影名")
    private String filmName;

    @ApiModelProperty("语言")
    private String filmLanguage;

    @ApiModelProperty("语言")
    private String filmCover;

    @ApiModelProperty("视觉：2D；3D")
    private String showingsVision;

    //将Long转成string
    @JsonSerialize(using = ToStringSerializer.class)
    //“type”：设置主键类型、如果数据库主键设置了自增建议使用“AUTO”(value="",type=Idtype.AUTO)
    @ApiModelProperty("影院ID")
    private Long cinemaId;

    @ApiModelProperty("影院名")
    private String cinemaName;

    @ApiModelProperty("地址")
    private String cinemaAddress;

    @ApiModelProperty("影院坐标")
    private Result cinemaLocation;

    //将Long转成string
    @JsonSerialize(using = ToStringSerializer.class)
//“type”：设置主键类型、如果数据库主键设置了自增建议使用“AUTO”(value="",type=Idtype.AUTO)
    @ApiModelProperty("放映厅ID")
    private Long hallId;

    @ApiModelProperty("放映厅号")
    private String hallNumber;

    @ApiModelProperty("影厅类型：IMAX厅、CGS中国巨幕厅、Dolby Cinema厅、ReaID厅、4K厅、ReaID 6FL厅、LUXE巨幕厅、4DX厅、DTS:X临境音厅、4D厅、巨幕厅")
    private String cinemaType;

    //将Long转成string
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("场次ID")
    private Long showingsId;

    @ApiModelProperty("座位ID列表")
    @TableField(value = "SEAT_IDLIST")
    private String seatIdlist;

    @ApiModelProperty("座位位置")
    private String hallPosition;

    @ApiModelProperty("开始时间")
    private String filmstartTime;

    @ApiModelProperty("结束时间")
    private String filmendTime;

    @ApiModelProperty("价格")
    private String billPrice;

    @ApiModelProperty("座位数量")
    private String billNumber;

    @ApiModelProperty("下单时间")
    private String createBill;

    @ApiModelProperty("订单状态，0待使用，1已完成")
    private Integer billState;


}
