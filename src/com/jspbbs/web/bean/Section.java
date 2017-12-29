package com.jspbbs.web.bean;

import com.jspbbs.core.annotation.IgnoreField;
import com.jspbbs.core.annotation.Table;
import com.jspbbs.core.db.CommonDao;

import java.util.Date;

@Table("bbs_section")
public class Section {
    private Integer id;
    private String name;
    private Integer masterID;
    private Date createTime;
    @IgnoreField
    private User master;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMasterID() {
        return masterID;
    }

    public void setMasterID(Integer masterID) {
        this.masterID = masterID;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public User getMaster() {
        if (null != master)
            return master;
        master = CommonDao.getInstance().findByID(User.class, masterID);
        return master;
    }
}
