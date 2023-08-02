package com.Ambition.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolePermission {
    private int id;
    private int roleId;
    private int permissionId;
}
