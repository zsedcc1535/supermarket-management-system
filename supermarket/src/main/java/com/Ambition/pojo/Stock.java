package com.Ambition.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stock {

    private int id;

    private int goodsId;

    private int goodsAmount;

    private int standardAmount;
    //
    private String goodsName;
    private String goods_name;
    private String name;
    private String underStock;
}