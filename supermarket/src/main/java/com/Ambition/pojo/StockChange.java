package com.Ambition.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockChange {
    private int id;

    private int goodsId;

    private int amount;

    private Date time;

    private int type;

    private int userId;

    private int state;

    private String goodsName;

    private String dataTime;
    private String name;
    private String username;
    private String outInType;
}