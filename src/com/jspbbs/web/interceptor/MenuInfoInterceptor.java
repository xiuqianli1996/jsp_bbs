package com.jspbbs.web.interceptor;

import com.jspbbs.core.aop.ActionContext;
import com.jspbbs.core.aop.AopAction;
import com.jspbbs.web.service.PermissionService;

public class MenuInfoInterceptor implements AopAction {

    private PermissionService permissionService = PermissionService.me;

    @Override
    public void invoke(ActionContext context) {
        context.getHttpContext().setAttr("permissions", permissionService.getAllPermission());
        context.invoke();
    }
}
