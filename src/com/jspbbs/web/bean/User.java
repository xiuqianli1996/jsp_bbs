package com.jspbbs.web.bean;

import com.jspbbs.core.annotation.IgnoreField;
import com.jspbbs.core.annotation.PrimaryKey;
import com.jspbbs.core.annotation.Table;
import com.jspbbs.core.db.CommonDao;

import java.util.*;
import java.util.stream.Collectors;

@Table("bbs_user")
public class User{
    @PrimaryKey
    private Integer id;
    private String username;
    private String password;
    private Date regTime;
    @IgnoreField
    private List<Topic> topicList;
    @IgnoreField
    private List<Reply> replyList;
    @IgnoreField
    private List<Role> roles;
    @IgnoreField
    private List<Permission> permissions;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    public List<Topic> getTopicList() {
        if (null != topicList){
            return topicList;
        }
        topicList = CommonDao.getInstance().find(Topic.class, "authorID=?", getId());
        return topicList;
    }

    public List<Reply> getReplyList() {
        if (null != replyList){
            return replyList;
        }
        replyList = CommonDao.getInstance().find(Reply.class, "userID=?", getId());
        return replyList;
    }

    public List<Role> getRoles() {
        if (null!= roles)
            return roles;
        CommonDao dao = CommonDao.getInstance();
        List<UserRole> userRoles = dao.find(UserRole.class, "userID=?", id);
        roles = new ArrayList<>();
        userRoles.forEach(userRole ->
                roles.add(dao.findByID(Role.class, userRole.getRoleID()))
        );
        return roles;
    }

    public List<Permission> getPermissions() {
        if (null != permissions)
            return permissions;
        Set<Permission> userPermissions = new HashSet<>();
        getRoles().forEach(role -> userPermissions.addAll(role.getPermissions()));
        permissions = userPermissions.stream().sorted(Comparator.comparing(Permission::getId)).collect(Collectors.toList());
        return permissions;
    }
}
