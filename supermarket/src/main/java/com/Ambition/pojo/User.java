package com.Ambition.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "用户实体类")
public class User {
    @ApiModelProperty(value ="用户id")
    private int id;
    @ApiModelProperty(value ="用户账号")
    private String userCode;
    @ApiModelProperty(value ="用户姓名")
    private String userName;
    @ApiModelProperty(value ="用户密码")
    private String password;
    @ApiModelProperty(value ="用户角色id")
    private Integer roleId;
    @ApiModelProperty(value ="用户头像")
    private String headPic;
    @ApiModelProperty(value ="薪水")
    private int salary;
    @ApiModelProperty(value ="用户状态")
    private Integer state;
    @ApiModelProperty(value ="用户电话")
    private String tel;
    @ApiModelProperty(value ="用户邮箱")
    private String email;
    @ApiModelProperty(value ="用户地址")
    private String address;

    private Role role;
}
