package com.jspbbs.web.controller;

import com.jspbbs.core.annotation.Around;
import com.jspbbs.core.db.Page;
import com.jspbbs.core.web.Controller;
import com.jspbbs.core.web.HttpContext;
import com.jspbbs.web.bean.Topic;
import com.jspbbs.web.interceptor.SectionInfoInterceptor;
import com.jspbbs.web.service.TopicService;

import javax.servlet.annotation.WebServlet;

@WebServlet(name = "SectionController", urlPatterns = "/section")
@Around(SectionInfoInterceptor.class)
public class SectionController extends Controller {

    private TopicService topicService = TopicService.me;

    public void index(HttpContext context){

        int pageNumber = context.getParamInt("pageNumber", 1);
        int pageSize = context.getParamInt("pageSize", 15);
        Page<Topic> page = new Page<>(pageNumber,pageSize);

        if (context.containsParam("id")){
            page = topicService.getTopicPageBySectionID(context.getParamInt("id"), page);
        } else {
            page = topicService.getAllTopicPage(page);
        }

        context.setAttr("page", page)
                .keepParam("id")
                .renderJSP("topic_list.jsp");
    }

}
