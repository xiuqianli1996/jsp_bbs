package com.jspbbs.web.controller.admin;

import com.jspbbs.core.web.Controller;
import com.jspbbs.core.web.HttpContext;
import com.jspbbs.web.bean.Permission;
import com.jspbbs.web.bean.Role;
import com.jspbbs.web.service.PermissionService;
import com.jspbbs.web.service.RoleService;

import javax.servlet.annotation.WebServlet;
import java.util.List;

@WebServlet(urlPatterns = "/admin/role")
public class SysRoleController extends Controller {

    private RoleService roleService = RoleService.me;
    private PermissionService permissionService = PermissionService.me;

    @Override
    public String getBaseViewPath() {
        return "/admin/role";
    }

    public void index(HttpContext context){
        String kw = context.getParam("kw");
        context.setAttr("beanList", roleService.getListBySearch(kw))
                .keepParam("kw")
                .renderJSP("list.jsp");
    }

    public void delete(HttpContext context){
        roleService.deleteByID(context.getParamInt("id"));
        context.setAttr("beanList", roleService.list())
                .renderJSP("list.jsp");
    }

    public void save(HttpContext context){
        Role bean = context.getBean(Role.class);
        roleService.saveOrUpdate(bean, context.getParams("permissionID"));
        context.setAttr("beanList", roleService.list())
                .renderJSP("list.jsp");
    }

    public void edit(HttpContext context){
        if (context.containsParam("id")){
            context.setAttr("bean", roleService.get(context.getParamInt("id")));
        }

        List<Permission> permissions = permissionService.list();

        context.setAttr("permissions", permissions)
                .renderJSP("edit.jsp");
    }
}
