package com.jspbbs.web.controller;

import com.jspbbs.core.web.Controller;
import com.jspbbs.core.web.HttpContext;
import com.jspbbs.web.bean.Permission;
import com.jspbbs.web.bean.User;
import com.jspbbs.web.exception.UserExistsException;
import com.jspbbs.web.service.UserService;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserController extends Controller {

    private UserService userService = UserService.me;

    public void login(HttpContext context){
        User user = context.getBean(User.class);
        user = userService.login(user);
        if (null != user){

            context.setSessionAttr("user", user)
                    .setAttr("msg", "登录成功");
        } else {
            context.setAttr("msg", "登录失败，用户名或密码错误");
        }
        context.setAttr("url", "#")
                .setAttr("linkName", "首页")
                .renderJSP("show_msg.jsp");
    }

    public void register(HttpContext context){
        User user = context.getBean(User.class);
        user.setRegTime(new Date());

        String msg = null;

        try {
            user = userService.register(user);
            context.setSessionAttr("user", user);
            msg = "注册成功";
        } catch (UserExistsException e) {
            msg = "注册失败,用户名已存在";
            e.printStackTrace();
        } finally {
            context.setAttr("msg", msg)
                    .setAttr("url", "#")
                    .setAttr("linkName", "首页")
                    .renderJSP("show_msg.jsp");
        }

    }

    public void logout(HttpContext context) throws IOException {
        context.removeSessionAttr("user")
                .redirect("#");
    }

}
