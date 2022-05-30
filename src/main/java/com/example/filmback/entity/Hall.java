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
 * @since 2022-04-21
 */
@Getter
@Setter
@TableName("hall_info")
@ApiModel(value = "Hall对象", description = "")
public class Hall implements Serializable {

    private static final long serialVersionUID = 1L;

    //将Long转成string
    @JsonSerialize(using = ToStringSerializer.class)
//“type”：设置主键类型、如果数据库主键设置了自增建议使用“AUTO”(value="",type=Idtype.AUTO)
    @TableId(value = "Hall_ID")
    @ApiModelProperty("放映厅ID")
    private Long hallId;
    @ApiModelProperty("放映厅号")
    private String hallNumber;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("影院ID")
    private Long cinemaId;

    @ApiModelProperty("影院名")
    private String cinemaName;

    @ApiModelProperty("影厅类型：IMAX厅、CGS中国巨幕厅、Dolby Cinema厅、ReaID厅、4K厅、ReaID 6FL厅、LUXE巨幕厅、4DX厅、DTS:X临境音厅、4D厅、巨幕厅")
    private String cinemaType;


}
