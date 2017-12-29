package com.jspbbs.web.bean;

import com.jspbbs.core.annotation.IgnoreField;
import com.jspbbs.core.annotation.PrimaryKey;
import com.jspbbs.core.annotation.Table;
import com.jspbbs.core.db.CommonDao;

import java.util.Date;

@Table("bbs_reply")
public class Reply {
    @PrimaryKey
    private Integer id;
    private String content;
    private Integer topicID;
    private Integer userID;
    private Date createTime;
    private Integer pid;
    private Integer level;
    @IgnoreField
    private User user;
    @IgnoreField
    private Topic topic;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getTopicID() {
        return topicID;
    }

    public void setTopicID(Integer topicID) {
        this.topicID = topicID;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public User getUser() {
        if (null != user)
            return user;
        user = CommonDao.getInstance().findByID(User.class, getUserID());
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Topic getTopic() {
        if (null != topic)
            return topic;
        topic = CommonDao.getInstance().findByID(Topic.class, getTopicID());
        return topic;
    }
}
