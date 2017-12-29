package com.jspbbs.core.web;

import com.jspbbs.core.annotation.Around;
import com.jspbbs.core.aop.ActionContext;
import com.jspbbs.core.aop.AopAction;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ControllerUtil {

    public static void dispatchAction(HttpServlet target, HttpContext httpContext) throws IOException {

        HttpServletRequest req = httpContext.getRequest();
        HttpServletResponse resp = httpContext.getResponse();

        Class clazz = target.getClass();

        try {

            String action = req.getParameter("action");
            if(null == action || action.equals("")){
                action = "index"; //默认进入index
            }

            Method actionMethod = null;

            try {
                actionMethod = clazz.getMethod(action, HttpContext.class);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

            if(null == actionMethod){
                resp.sendError(404);
                return;
            }

            if (clazz.isAnnotationPresent(Around.class)){
                Around around = (Around) clazz.getAnnotation(Around.class);
                Class<? extends AopAction> aopActionClass = around.value();
                AopAction aopAction = aopActionClass.newInstance();

                ActionContext actionContext = new ActionContext(target, actionMethod, new Object[]{httpContext});

                aopAction.invoke(actionContext);
                return;
            }

            if (actionMethod.isAnnotationPresent(Around.class)){
                Around around = actionMethod.getAnnotation(Around.class);
                Class<? extends AopAction> aopActionClass = around.value();
                AopAction aopAction = aopActionClass.newInstance();

                ActionContext actionContext = new ActionContext(target, actionMethod, new Object[]{httpContext});

                actionContext.setOnMethod(true);

                aopAction.invoke(actionContext);
                return;
            }

            actionMethod.invoke(target, httpContext);

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
