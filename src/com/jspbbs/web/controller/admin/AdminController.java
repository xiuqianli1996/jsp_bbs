package com.jspbbs.web.controller.admin;

import com.jspbbs.core.annotation.Around;
import com.jspbbs.core.db.CommonDao;
import com.jspbbs.core.web.Controller;
import com.jspbbs.core.web.HttpContext;
import com.jspbbs.web.bean.Section;
import com.jspbbs.web.bean.Topic;
import com.jspbbs.web.bean.User;
import com.jspbbs.web.interceptor.MenuInfoInterceptor;

import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = "/admin")
public class AdminController extends Controller {

//    private ITopicService topicService = new TopicService();
//    private ISectionService sectionService = new SectionService();
//    private IUserService userService = new UserService();
    private CommonDao dao = CommonDao.getInstance();

    public void index(HttpContext context){
        context.setAttr("topicCount", dao.findCount(Topic.class))
                .setAttr("sectionCount", dao.findCount(Section.class))
                .setAttr("userCount", dao.findCount(User.class))
                .renderJSP("admin/index.jsp");
    }

}
