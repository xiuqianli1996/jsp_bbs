package com.jspbbs.core.db;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Condition {
    private StringBuilder where = new StringBuilder();
    private List<Object> paras = new ArrayList<>();
    private StringBuilder orderByStr;

    public static Condition create(String key, String operator, Object para){
        Condition condition = new Condition();
        condition.where.append("`")
                .append(key)
                .append("` ")
                .append(operator)
                .append(" ? ");

        condition.paras.add(para);
        return condition;

    }

    public Condition and(String key, String operator, Object para){
        where.append("AND `")
                .append(key)
                .append("` ")
                .append(operator)
                .append(" ? ");
        paras.add(para);
        return this;
    }

    public Condition or(String key, String operator, Object para){
        where.append("OR `")
                .append(key)
                .append("` ")
                .append(operator)
                .append(" ? ");
        paras.add(para);
        return this;
    }

    public Condition orderBy(String key, String keyword){
        if (null == orderByStr){
            orderByStr = new StringBuilder(" ORDER BY ");
        } else {
            orderByStr.append(',');
        }

        orderByStr.append('`').append(key).append("` ").append(keyword);

        return this;
    }

    public Condition asc(String key){
        return orderBy(key, "ASC");
    }

    public Condition desc(String key){
        return orderBy(key, "DESC");
    }

    public StringBuilder getWhere() {
        if (null != orderByStr)
            where.append(orderByStr);
        return where;
    }

    public Object[] getParas() {
        return paras.toArray();
    }
}
