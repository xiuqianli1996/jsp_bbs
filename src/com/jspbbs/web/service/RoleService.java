package com.jspbbs.web.service;

import com.jspbbs.core.db.Condition;
import com.jspbbs.core.service.Service;
import com.jspbbs.web.bean.Role;
import com.jspbbs.web.bean.RolePermission;

import java.util.List;

public class RoleService extends Service<Role> {

    public static RoleService me = new RoleService();

    public List<Role> getListBySearch(String kw) {
        Condition condition = null;
        if (null != kw && !"".equals(kw)){
            condition = Condition.create("name", "like", '%' + kw + '%');
        }
        return dao.find(Role.class, condition);
    }

    @Override
    public boolean deleteByID(int id) {
        dao.delete(RolePermission.class, "roleID=?", id);//清除关系
        return super.deleteByID(id);
    }

    public boolean saveOrUpdate(Role bean, String[] permissionIDs){
        if (null != bean.getId()){
            dao.delete(RolePermission.class, "roleID=?", bean.getId());//清除修改前的关系
            update(bean);
        } else {
            bean = dao.insert(Role.class, bean);
        }

        if (null != permissionIDs){
            for (String idStr : permissionIDs){//保存新的对应关系
                Integer id = Integer.parseInt(idStr);
                RolePermission rolePermission = new RolePermission(bean.getId(), id);
                dao.insert(RolePermission.class, rolePermission);
            }
        }

        return true;
    }
}
