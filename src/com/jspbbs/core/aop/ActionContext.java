package com.jspbbs.core.aop;

import com.jspbbs.core.annotation.Around;
import com.jspbbs.core.web.HttpContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ActionContext {

    private Object target;
    private HttpContext httpContext;
    private Method method;
    private Object[] args;
    private boolean isOnMethod = false;

    public ActionContext(Object target, Method method, Object[] args) {
        this.target = target;
        this.method = method;
        this.args = args;

        if(null != args && args[0] instanceof HttpContext)
            httpContext = (HttpContext) args[0];
    }

    public HttpContext getHttpContext() {
        return httpContext;
    }

    public void setHttpContext(HttpContext httpContext) {
        this.httpContext = httpContext;
    }

    public boolean isOnMethod() {
        return isOnMethod;
    }

    public void setOnMethod(boolean onMethod) {
        isOnMethod = onMethod;
    }

    public void invoke(){
        if (null == target || null == method)
            return;
        try {
            if (!isOnMethod && method.isAnnotationPresent(Around.class)){//方法级拦截
                Around around = method.getAnnotation(Around.class);
                Class<? extends AopAction> aopActionClass = around.value();
                AopAction aopAction = aopActionClass.newInstance();

                //ActionContext actionContext = new ActionContext(target, method, args);
                setOnMethod(true);//设置当前上下文切点为方法
                aopAction.invoke(this);
                return;
            }

            method.invoke(target, args);
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
    }
}
