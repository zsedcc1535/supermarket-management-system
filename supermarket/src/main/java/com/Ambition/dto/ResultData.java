package com.Ambition.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "结果数据实体类")
public class ResultData<T> {
    @ApiModelProperty(value ="响应状态")
    private int code; //响应状态
    @ApiModelProperty(value ="返回信息")
    private String msg; //返回信息
    @ApiModelProperty(value ="/返回数据对象")
    private T data; //返回数据对象


}
