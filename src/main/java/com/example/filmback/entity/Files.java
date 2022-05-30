package com.example.filmback.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
@TableName("files_info")
public class Files {

    //将Long转成string
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "FILES_ID")
    private Long filesId;
    private String filesType;
    //将Long转成string
    @JsonSerialize(using = ToStringSerializer.class)
    private Long filesSize;
    private String filesUrl;
    private Boolean isDelete;
    private Boolean enable;
    private String md5;
}
