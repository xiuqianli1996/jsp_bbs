package com.jspbbs.web.bean;

import com.jspbbs.core.annotation.IgnoreField;
import com.jspbbs.core.annotation.PrimaryKey;
import com.jspbbs.core.annotation.Table;
import com.jspbbs.core.db.CommonDao;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Table("bbs_role")
public class Role {
    @PrimaryKey
    private Integer id;
    private String name;
    private String code;
    @IgnoreField
    List<Permission> permissions;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Permission> getPermissions() {
        if (null!= permissions)
            return permissions;
        CommonDao dao = CommonDao.getInstance();
        List<RolePermission> rolePermissions = dao.find(RolePermission.class, "roleID=?", id);
        permissions = new ArrayList<>();
        rolePermissions.forEach(rolePermission ->
                permissions.add(dao.findByID(Permission.class, rolePermission.getPermissionID()))
        );
        return permissions;
    }
}
