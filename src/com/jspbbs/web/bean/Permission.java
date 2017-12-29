package com.jspbbs.web.bean;

import com.jspbbs.core.annotation.PrimaryKey;
import com.jspbbs.core.annotation.Table;

@Table("bbs_permission")
public class Permission {
    @PrimaryKey
    private Integer id;
    private String name;
    private String code;
    private String url;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
