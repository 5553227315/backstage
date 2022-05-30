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
 * @since 2022-04-22
 */
@Getter
@Setter
@TableName("seat_showings")
@ApiModel(value = "SeatShowings对象", description = "")
public class SeatShowings implements Serializable {

    private static final long serialVersionUID = 1L;

    //将Long转成string
    @JsonSerialize(using = ToStringSerializer.class)
//“type”：设置主键类型、如果数据库主键设置了自增建议使用“AUTO”(value="",type=Idtype.AUTO)
    @TableId(value = "SEAT_ID")
    @ApiModelProperty("座位ID")
    private Long seatId;

    //将Long转成string
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("场次ID")
    private Long showingsId;

    @ApiModelProperty("横向位置")
    private Integer seatX;

    @ApiModelProperty("纵向位置")
    private Integer seatY;

    @ApiModelProperty("是否有人，1有人，0无人，2没座位，3禁用")
    private Integer seatState;

    @ApiModelProperty("座椅颜色")
    private String seatColor;

    @ApiModelProperty("座椅类型")
    private String seatType;




}
