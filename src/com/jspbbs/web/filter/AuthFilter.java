package com.jspbbs.web.filter;

import com.jspbbs.core.db.CommonDao;
import com.jspbbs.web.bean.Permission;
import com.jspbbs.web.bean.User;
import com.jspbbs.web.service.PermissionService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class AuthFilter implements Filter {

    private PermissionService permissionService = PermissionService.me;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        String ctx = request.getContextPath();
        String url = request.getRequestURI().replace(ctx, "").substring(1);
        Permission permission = permissionService.findByUrl(url);
        if (null != permission){
            User user = (User) request.getSession(true).getAttribute("user");
            if (null == user){
                response.sendError(403, "没有权限访问该页面");
                return;
            }

            if (user.getPermissions().stream().noneMatch(perm -> perm.getId().equals(permission.getId()))){
                response.sendError(403, "没有权限访问该页面");
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
