package com.jspbbs.web.interceptor;

import com.jspbbs.core.aop.ActionContext;
import com.jspbbs.core.aop.AopAction;
import com.jspbbs.core.web.HttpContext;
import com.jspbbs.web.bean.User;

public class UserAuthInterceptor implements AopAction {
    @Override
    public void invoke(ActionContext context) {
        HttpContext httpContext = context.getHttpContext();
        User user = (User) httpContext.getSessionAttr("user");
        if(null == user){
            httpContext.setAttr("msg", "未登录")
                    .setAttr("url", "/")
                    .setAttr("linkName", "首页")
                    .renderJSP("show_msg.jsp");
            return;
        }
        context.invoke();
    }
}
