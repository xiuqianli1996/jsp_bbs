package com.jspbbs.core.db;

import com.jspbbs.core.annotation.IgnoreField;
import com.jspbbs.core.annotation.Table;
import com.jspbbs.core.util.DBUtil;
import com.jspbbs.core.util.ReflectUtil;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Model<T> {

    public Dao<T> dao = new Dao<T>();

    public Model(){
        dao.settClass(ReflectUtil.getTypeClass(getClass()));
    }

    public boolean delete(){
        try {
            Field pkField = getClass().getDeclaredField(dao.getPK());
            Integer id = (Integer) pkField.get(this);
            return dao.deleteByID(id);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return false;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

    public T save(){
        return dao.insert((T) this);
    }

    public T update(){
        return dao.update((T) this);
    }

}
