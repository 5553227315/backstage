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
 * 影院场次表
 * </p>
 *
 * @author Wyz
 * @since 2022-03-25
 */
@Getter
@Setter
@TableName("film_showings")
@ApiModel(value = "Showings对象", description = "影院场次表")
public class Showings implements Serializable {

    //将Long转成string
    @JsonSerialize(using = ToStringSerializer.class)
//“type”：设置主键类型、如果数据库主键设置了自增建议使用“AUTO”(value="",type=Idtype.AUTO)
    @TableId(value = "Showings_ID")
    @ApiModelProperty("场次ID")
    private Long showingsId;

    @ApiModelProperty("影院ID")
    private Long cinemaId;

    @ApiModelProperty("影院名")
    private String cinemaName;

    @ApiModelProperty("电影ID")
    private Long filmId;

    @ApiModelProperty("电影名")
    private String filmName;

    @ApiModelProperty("电影评分")
    private String filmScore;

    @ApiModelProperty("开始时间")
    private String filmstartTime;

    @ApiModelProperty("片长")
    private Integer filmLength;

    @ApiModelProperty("结束时间")
    private String filmendTime;

    @ApiModelProperty("语言")
    private String filmLanguage;

    @ApiModelProperty("视觉：2D；3D")
    private String showingsVision;

    @ApiModelProperty("放映厅号")
    private String hallNumber;

    @ApiModelProperty("本场价格")
    private String showingsPrice;



}
