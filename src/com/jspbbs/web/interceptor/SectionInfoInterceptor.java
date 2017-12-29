package com.jspbbs.web.interceptor;

import com.jspbbs.core.aop.ActionContext;
import com.jspbbs.core.aop.AopAction;
import com.jspbbs.core.web.HttpContext;
import com.jspbbs.web.bean.Section;
import com.jspbbs.web.bean.User;
import com.jspbbs.web.service.SectionService;

import java.util.List;

public class SectionInfoInterceptor implements AopAction {

    private SectionService sectionService = SectionService.me;

    @Override
    public void invoke(ActionContext context) {
        HttpContext httpContext = context.getHttpContext();
        List<Section> sectionList = sectionService.list();
        httpContext.setAttr("sectionList", sectionList);

        User user = (User)httpContext.getSessionAttr("user");
        if(null != user){

        }

        context.invoke();
    }
}
