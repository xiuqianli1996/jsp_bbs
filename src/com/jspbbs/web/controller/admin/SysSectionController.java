package com.jspbbs.web.controller.admin;

import com.jspbbs.core.annotation.Around;
import com.jspbbs.core.web.Controller;
import com.jspbbs.core.web.HttpContext;
import com.jspbbs.web.bean.Section;
import com.jspbbs.web.bean.User;
import com.jspbbs.web.interceptor.MenuInfoInterceptor;
import com.jspbbs.web.service.SectionService;
import com.jspbbs.web.service.UserService;

import javax.servlet.annotation.WebServlet;
import java.util.List;

@WebServlet(urlPatterns = "/admin/section")
public class SysSectionController extends Controller {
    private SectionService sectionService = SectionService.me;
    private UserService userService = UserService.me;

    @Override
    public String getBaseViewPath() {
        return "/admin/section";
    }

    public void index(HttpContext context){
        String kw = context.getParam("kw");
        context.setAttr("sections", sectionService.getSectionsBySearch(kw))
                .keepParam("kw")
                .renderJSP("list.jsp");
    }

    public void delete(HttpContext context){
        sectionService.deleteByID(context.getParamInt("id"));
        context.setAttr("sections", sectionService.list())
                .renderJSP("list.jsp");
    }

    public void save(HttpContext context){
        Section bean = context.getBean(Section.class);
        if (null == bean.getId())
            sectionService.save(bean);
        else
            sectionService.update(bean);
        context.setAttr("sections", sectionService.list())
                .renderJSP("list.jsp");
    }

    public void edit(HttpContext context){
        if (context.containsParam("id")){
            context.setAttr("bean", sectionService.get(context.getParamInt("id")));
        }
        List<User> users = userService.list();
        context.setAttr("users", users)
                .renderJSP("edit.jsp");
    }
}
