package com.jspbbs.web.bean;

import com.jspbbs.core.annotation.Table;

@Table("bbs_role_permission")
public class RolePermission {
    private Integer roleID;
    private Integer permissionID;

    public RolePermission() {
    }

    public RolePermission(Integer roleID, Integer permissionID) {
        this.roleID = roleID;
        this.permissionID = permissionID;
    }

    public Integer getRoleID() {
        return roleID;
    }

    public void setRoleID(Integer roleID) {
        this.roleID = roleID;
    }

    public Integer getPermissionID() {
        return permissionID;
    }

    public void setPermissionID(Integer permissionID) {
        this.permissionID = permissionID;
    }
}
