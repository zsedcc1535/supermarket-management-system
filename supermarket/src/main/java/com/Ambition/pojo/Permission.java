package com.Ambition.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "权限实体类")
public class Permission {
    @ApiModelProperty(value ="权限id")
    private int id;
    @ApiModelProperty(value ="权限编号")
    private int code;
    @ApiModelProperty(value ="权限名")
    private String permissionName;

}