package com.example.filmback.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 电影信息表
 * </p>
 *
 * @author Wyz
 * @since 2022-03-23
 */
@Getter
@Setter
@TableName("film_info")
@ApiModel(value = "Film对象", description = "电影信息表")
public class Film implements Serializable {

    //将Long转成string
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("电影ID")
    @TableId(value = "FILM_ID")
    private Long filmId;

    @ApiModelProperty("电影名")
    private String filmName;

    @ApiModelProperty("导演")
    private String director;

    //将Long转成string
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("片长")
    private Integer filmLength;

    @ApiModelProperty("电影类型：动作片、冒险片、喜剧片、剧情片、幻想片、恐怖片、爱情片、历史片")
    private String filmType;

    @ApiModelProperty("片源地")
    private String filmSource;

    @ApiModelProperty("上映时间")
    private String releaseDate;

    @ApiModelProperty("剧情介绍")
    private String plotIntro;

    @ApiModelProperty("封面")
    private String filmCover;

    @ApiModelProperty("电影评分")
    @TableField(exist = false)
    private String filmScore;

    private String canShow;

    private String createFilm;


    private String performer;

    private String filmLanguage;
    @TableField(exist = false)
    private Integer evaLength;
    @TableField(exist = false)
    private Integer maxlength;

}
