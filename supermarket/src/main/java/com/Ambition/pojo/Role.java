package com.Ambition.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(value = "角色实体类")
public class Role {
    @ApiModelProperty(value ="角色id")
    private int id;
    @ApiModelProperty(value ="角色名")
    private String rolename;
    @ApiModelProperty(value ="默认薪水")
    private int salary;

    private List<Permission> permissions;
}
