package com.Ambition.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "商品实体类")
public class Goods {
    @ApiModelProperty(value ="商品id")
    private int id;

    private String goodsName;

    private String goodsDesc;

    private Date productTime;

    private int shelfLife;

    private int typeId;

    private int expirationTime;

    private int state;

    private String price;
    //类型名
    private String name;
    private String myDate;
}