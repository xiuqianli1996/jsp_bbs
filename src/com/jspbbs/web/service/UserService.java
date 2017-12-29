package com.jspbbs.web.service;

import com.jspbbs.core.db.Condition;
import com.jspbbs.core.service.Service;
import com.jspbbs.web.bean.Role;
import com.jspbbs.web.bean.RolePermission;
import com.jspbbs.web.bean.User;
import com.jspbbs.web.bean.UserRole;
import com.jspbbs.web.exception.UserExistsException;

import java.util.List;

public class UserService extends Service<User>{

    public static UserService me = new UserService();

    /**注册
     * @param user
     * @return
     */
    public User register(User user) throws UserExistsException {
        Condition condition = Condition.create("username", "=", user.getUsername());
        if(null != dao.findOne(User.class, condition)){//用户名不能重复
            throw new UserExistsException();
        }
        return dao.insert(User.class, user);
    }

    public User login(User user) {
        Condition condition = Condition.create("username", "=", user.getUsername())
                .and("password", "=", user.getPassword());
        return dao.findOne(User.class, condition);
    }

    public List<User> getListBySearch(String kw) {
        Condition condition = null;
        if (null != kw && !"".equals(kw)){
            condition = Condition.create("username", "like", '%' + kw + '%');
        }
        return dao.find(User.class, condition);
    }

    @Override
    public boolean deleteByID(int id) {
        dao.delete(UserRole.class, "userID=?", id);//清除关系
        return super.deleteByID(id);
    }

    public boolean saveOrUpdate(User bean, String[] roleIDs){
        if (null != bean.getId()){
            dao.delete(UserRole.class, "userID=?", bean.getId());//清除修改前的关系

            update(bean);
        } else {
            bean = dao.insert(User.class, bean);
        }

        if (null != roleIDs){
            for (String idStr : roleIDs){//保存新的对应关系
                Integer id = Integer.parseInt(idStr);
                UserRole userRole = new UserRole(bean.getId(), id);
                dao.insert(UserRole.class, userRole);
            }
        }

        return true;
    }
}
