package com.jspbbs.web.service;

import com.jspbbs.core.db.Condition;
import com.jspbbs.core.service.Service;
import com.jspbbs.web.bean.Permission;

import java.util.List;

public class PermissionService extends Service<Permission> {

    public static PermissionService me = new PermissionService();

    public List<Permission> getAllPermission() {
        return dao.findAll(Permission.class);
    }

    public List<Permission> getListBySearch(String kw){
        Condition condition = null;
        if (null != kw && !"".equals(kw)){
            condition = Condition.create("name", "like", '%' + kw + '%');
        }
        return dao.find(Permission.class, condition);
    }

    public Permission findByUrl(String url){
        return dao.findOne(Permission.class, Condition.create("url", "=", url));
    }
}
