package com.jspbbs.web.service;

import com.jspbbs.core.db.Condition;
import com.jspbbs.core.db.Page;
import com.jspbbs.core.service.Service;
import com.jspbbs.web.bean.Topic;

import java.util.Date;
import java.util.List;

public class TopicService extends Service<Topic> {

    public static TopicService me = new TopicService();

    @Override
    public boolean save(Topic topic) {
        topic.setCreateTime(new Date());
        topic.setViewCount(0);
        return super.save(topic);
    }

    public void addViewCount(Topic topic){
        topic.setViewCount(topic.getViewCount() + 1);
        update(topic);
    }

    public Page<Topic> getTopicPageBySectionID(int sectionID, Page<Topic> page) {
        //return topicDao.paginateBySectionID(sectionID, page);
        Condition condition = Condition.create("sectionID", "=", sectionID);
        return dao.paginate(Topic.class, page, condition);
    }

    public Page<Topic> getTopicPageByAuthorID(int authorID, Page<Topic> page) {
        Condition condition = Condition.create("authorID", "=", authorID);
        return dao.paginate(Topic.class, page, condition);
    }

    public List<Topic> getTopicsBySearch(Integer sectionID, String kw) {
        Condition condition = null;
        if (null != sectionID){
            condition = Condition.create("sectionID", "=", sectionID);
        }

        if (null != kw && !"".equals(kw)){
            if (null == condition)
                condition = Condition.create("title", "like", '%' + kw + '%');
            else
                condition.and("title", "like", '%' + kw + '%');
        }

        return dao.find(Topic.class, condition);
    }

    public Page<Topic> paginateBySearch(Page<Topic> page, String kw){
        return dao.paginate(Topic.class, page, Condition.create("title", "like", '%' + kw + '%'));
    }

    public Page<Topic> getAllTopicPage(Page<Topic> page){
        return dao.paginate(Topic.class,page, "");
    }
}
