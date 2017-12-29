package com.jspbbs.core.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ReflectUtil {

    public static Class getTypeClass(Class clazz){
        Class tClass = null;
        Type genericSuperclass = clazz.getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType){
            tClass = (Class)((ParameterizedType)genericSuperclass).getActualTypeArguments()[0];
        } else {
            tClass = (Class)genericSuperclass;
        }

        return tClass;
    }
}
