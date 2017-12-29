package com.jspbbs.web.bean;

import com.jspbbs.core.annotation.Table;

@Table("bbs_user_role")
public class UserRole {
    private Integer userID;
    private Integer roleID;

    public UserRole() {
    }

    public UserRole(Integer userID, Integer roleID) {
        this.userID = userID;
        this.roleID = roleID;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Integer getRoleID() {
        return roleID;
    }

    public void setRoleID(Integer roleID) {
        this.roleID = roleID;
    }
}
