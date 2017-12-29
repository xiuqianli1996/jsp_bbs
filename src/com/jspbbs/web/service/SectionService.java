package com.jspbbs.web.service;

import com.jspbbs.core.db.Condition;
import com.jspbbs.core.service.Service;
import com.jspbbs.web.bean.Section;

import java.util.List;

public class SectionService extends Service<Section> {

    public static SectionService me = new SectionService();

    public List<Section> getSectionsBySearch(String kw) {
        Condition condition = null;
        if (null != kw && !"".equals(kw)){
            condition = Condition.create("name", "like", '%' + kw + '%');
        }
        return dao.find(Section.class, condition);
    }

}
