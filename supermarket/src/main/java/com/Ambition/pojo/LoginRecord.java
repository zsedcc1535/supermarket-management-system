package com.Ambition.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRecord {
    private int id;
    private String usercode;
    private String username;
    private String roleName;
    private Date loginTime;
}
