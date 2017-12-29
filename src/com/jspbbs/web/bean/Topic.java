package com.jspbbs.web.bean;

import com.jspbbs.core.annotation.IgnoreField;
import com.jspbbs.core.annotation.PrimaryKey;
import com.jspbbs.core.annotation.Table;
import com.jspbbs.core.db.CommonDao;

import java.util.Date;
import java.util.List;

@Table("bbs_topic")
public class Topic {
    @PrimaryKey
    private Integer id;
    private String title;
    private String content;
    private Integer authorID;
    private Integer sectionID;
    private Date createTime;
    private Integer viewCount;
    @IgnoreField
    private User author;
    @IgnoreField
    private Section section;
    @IgnoreField
    private List<Reply> replyList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getAuthorID() {
        return authorID;
    }

    public void setAuthorID(Integer authorID) {
        this.authorID = authorID;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getSectionID() {
        return sectionID;
    }

    public void setSectionID(Integer sectionID) {
        this.sectionID = sectionID;
    }

    public User getAuthor() {
        if (null != author){
            return author;
        }
        author = CommonDao.getInstance().findByID(User.class, authorID);
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Section getSection() {
        if (null != section){
            return section;
        }
        section = CommonDao.getInstance().findByID(Section.class, sectionID);
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public List<Reply> getReplyList() {
        if (null != replyList){
            return replyList;
        }
        replyList = CommonDao.getInstance().find(Reply.class, "topicID=?", getId());
        return replyList;
    }

    public void setReplyList(List<Reply> replyList) {
        this.replyList = replyList;
    }
}
