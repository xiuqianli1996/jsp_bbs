package com.jspbbs.web.controller.admin;

import com.jspbbs.core.web.Controller;
import com.jspbbs.core.web.HttpContext;
import com.jspbbs.web.bean.Role;
import com.jspbbs.web.bean.User;
import com.jspbbs.web.service.RoleService;
import com.jspbbs.web.service.UserService;

import javax.servlet.annotation.WebServlet;
import java.util.List;

@WebServlet(urlPatterns = "/admin/user")
public class SysUserController extends Controller {

    private UserService userService = UserService.me;
    private RoleService roleService = RoleService.me;

    @Override
    public String getBaseViewPath() {
        return "/admin/user";
    }

    public void index(HttpContext context){
        String kw = context.getParam("kw");
        context.setAttr("beanList", userService.getListBySearch(kw))
                .keepParam("kw")
                .renderJSP("list.jsp");
    }

    public void delete(HttpContext context){
        userService.deleteByID(context.getParamInt("id"));
        context.setAttr("beanList", userService.list())
                .renderJSP("list.jsp");
    }

    public void save(HttpContext context){
        User bean = context.getBean(User.class);
        userService.saveOrUpdate(bean, context.getParams("roleID"));
        context.setAttr("beanList", userService.list())
                .renderJSP("list.jsp");
    }

    public void edit(HttpContext context){
        if (context.containsParam("id")){
            context.setAttr("bean", userService.get(context.getParamInt("id")));
        }

        List<Role> roles = roleService.list();

        context.setAttr("roles", roles)
                .renderJSP("edit.jsp");
    }
}
