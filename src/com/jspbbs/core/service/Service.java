package com.jspbbs.core.service;

import com.jspbbs.core.db.CommonDao;
import com.jspbbs.core.db.Page;
import com.jspbbs.core.util.ReflectUtil;

import java.util.List;

public class Service<T> implements ServiceInterface<T> {
    protected CommonDao dao = CommonDao.getInstance();
    private Class<T> beanClass = null;
    protected Class<T> getBeanClass(){
        if (null != beanClass){
            return beanClass;
        }
        beanClass = ReflectUtil.getTypeClass(getClass());

        return beanClass;
    }

    public List<T> list(){
        return dao.findAll(getBeanClass());
    }

    public T get(int id){
        return dao.findByID(getBeanClass(), id);
    }

    public boolean save(T t){
        return null != dao.insert(getBeanClass(), t);
    }

    public boolean update(T t){
        return null != dao.update(getBeanClass(), t);
    }

    public boolean deleteByID(int id){
        return dao.deleteByID(getBeanClass(), id);
    }

}
