package com.jspbbs.core.web;

import com.jspbbs.core.util.InjectUtil;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class HttpContext {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private String baseViewPath;

    public HttpContext(HttpServletRequest request, HttpServletResponse response, String baseViewPath) {
        this.request = request;
        this.response = response;
        this.baseViewPath = baseViewPath;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public String getBaseViewPath() {
        return baseViewPath;
    }

    public void setBaseViewPath(String baseViewPath) {
        this.baseViewPath = baseViewPath;
    }

    public void renderJSP(String jspName){
        String baseViewPath = getBaseViewPath() == null || "".equals(getBaseViewPath()) ? "" : getBaseViewPath();
        if (!baseViewPath.endsWith("/")){ //如果视图路径不以/结尾就加上，保证jsp路径是绝对路径
            baseViewPath += '/';
        }
        try {
            request.getRequestDispatcher(baseViewPath + jspName).forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void redirect(String url) throws IOException {
        response.sendRedirect(url);
    }

    public String getParam(String name){
        return request.getParameter(name);
    }

    public String[] getParams(String name){
        return request.getParameterValues(name);
    }

    public boolean containsParam(String name){
        String param = request.getParameter(name);
        return null != request.getParameter(name) && !"".equals(param);
    }

    public Integer getParamInteger(String name){
        if(!containsParam(name))
            return null;
        return Integer.parseInt(getParam(name));
    }

    public int getParamInt(String name){
        return getParamInteger(name);
    }

    public int getParamInt(String name, int defaultValue){
        if(!containsParam(name))
            return defaultValue;
        return Integer.parseInt(getParam(name));
    }

    public HttpContext keepParam(String name){
        return setAttr(name, getParam(name));
    }

    public HttpContext setAttr(String key, Object value){
        request.setAttribute(key, value);
        return this;
    }

    public Object getAttr(String key){
        return request.getAttribute(key);
    }

    public HttpContext setSessionAttr(String key, Object value){
        request.getSession(true).setAttribute(key, value);
        return this;
    }

    public HttpContext removeSessionAttr(String key){
        request.getSession(true).removeAttribute(key);
        return this;
    }

    public Object getSessionAttr(String key){
        return request.getSession(true).getAttribute(key);
    }

    public HttpContext addCookie(String key, String value, int expiry){
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(expiry);
        response.addCookie(cookie);
        return this;
    }

    public Cookie getCookie(String key){
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies){
            if (key.equals(cookie.getName()))
                return cookie;
        }
        return null;
    }

    public void sendError(int code) throws IOException {
        response.sendError(code);
    }

    public <T> T getBean(Class<T> clazz){
        return InjectUtil.injectByRequest(clazz, getRequest());
    }
}
