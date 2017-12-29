package com.jspbbs.web.service;

import com.jspbbs.core.db.Condition;
import com.jspbbs.core.service.Service;
import com.jspbbs.web.bean.Reply;

import java.util.Date;

public class ReplyService extends Service<Reply> {

    public static ReplyService me = new ReplyService();

    @Override
    public boolean save(Reply reply) {
        reply.setCreateTime(new Date());
        Reply last = getLastByTopicID(reply.getTopicID());
        if (null == last){
            reply.setLevel(1);//当前帖子没有回帖，设为1楼
        } else {
            reply.setLevel(last.getLevel() + 1);
        }
        return super.save(reply);
    }

    public Reply getLastByTopicID(int topicID) {
        Condition condition = Condition.create("topicID", "=", topicID).desc("createTime");
        return dao.findOne(Reply.class, condition);
    }

}
