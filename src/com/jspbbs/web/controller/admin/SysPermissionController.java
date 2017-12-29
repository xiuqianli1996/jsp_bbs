package com.jspbbs.web.controller.admin;

import com.jspbbs.core.web.Controller;
import com.jspbbs.core.web.HttpContext;
import com.jspbbs.web.bean.Permission;
import com.jspbbs.web.service.PermissionService;

import javax.servlet.annotation.WebServlet;
import java.util.List;

@WebServlet(urlPatterns = "/admin/permission")
public class SysPermissionController extends Controller {

    private PermissionService permissionService = PermissionService.me;

    @Override
    public String getBaseViewPath() {
        return "/admin/permission";
    }

    public void index(HttpContext context){
        String kw = context.getParam("kw");
        context.setAttr("beanList", permissionService.getListBySearch(kw))
                .keepParam("kw")
                .renderJSP("list.jsp");
    }

    public void delete(HttpContext context){
        permissionService.deleteByID(context.getParamInt("id"));
        context.setAttr("beanList", permissionService.list())
                .renderJSP("list.jsp");
    }

    public void save(HttpContext context){
        Permission bean = context.getBean(Permission.class);
        if (null == bean.getId())
            permissionService.save(bean);
        else
            permissionService.update(bean);
        context.setAttr("beanList", permissionService.list())
                .renderJSP("list.jsp");
    }

    public void edit(HttpContext context){
        if (context.containsParam("id")){
            context.setAttr("bean", permissionService.get(context.getParamInt("id")));
        }
        context.renderJSP("edit.jsp");
    }
}
