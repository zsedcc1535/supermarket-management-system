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
@ApiModel(value = "订单类")
public class Order {
    @ApiModelProperty(value ="商品id")
    private int id;

    private String username;

    private String goodsName;

    private int goodsAmount;

    private Date time;

    private String MyData;
}
