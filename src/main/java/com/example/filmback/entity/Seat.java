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
 * @since 2022-04-27
 */
@Getter
@Setter
@TableName("seat_list")
@ApiModel(value = "Seat对象", description = "")
public class Seat implements Serializable {


    //将Long转成string
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "SEAT_ID")
    @ApiModelProperty("座位ID")
    private Long seatId;

    //将Long转成string
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("放映厅ID")
    private Long hallId;

    @ApiModelProperty("放映厅号")
    private String hallNumber;

    @ApiModelProperty("横向位置")
    private Integer seatX;

    @ApiModelProperty("纵向位置")
    private Integer seatY;

    @ApiModelProperty("是否有座位，0有，2无，3禁用")
    private Integer seatState;

    @ApiModelProperty("座椅颜色")
    private String seatColor;

    @ApiModelProperty("座椅类型")
    private String seatType;


}
