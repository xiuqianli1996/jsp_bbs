package com.jspbbs.core.util;

import com.jspbbs.core.annotation.IgnoreField;
import com.sun.deploy.net.HttpRequest;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class InjectUtil {

    public static  <T> T injectByRequest(Class<T> clazz, HttpServletRequest request){//将request提交的参数自动注入到javabean
        T bean = null;
        try {
            bean = clazz.newInstance();//生成bean实例
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields){
                field.setAccessible(true);
                String value = request.getParameter(field.getName());

                if(null == value || "".equals(value)){//请求中没有该字段或该字段没有值就跳过
                    continue;
                }
                field.set(bean, ConvertUtil.convert(value, field.getType()));//反射、给成员变量赋值
            }

        } catch (InstantiationException | IllegalAccessException e) {
            System.out.println("生成bean实例失败");
            e.printStackTrace();
        }
        return bean;
    }

    public static <T> List<T> injectByResultSet(Class<T> clazz, ResultSet resultSet){//将结果集自动注入到javabean,成员变量名必须和数据库字段对应
        List<T> result = new ArrayList<>();
        try {
            while (resultSet.next()){//遍历结果集
                T bean = clazz.newInstance();//生成bean实例

                Field[] fields = clazz.getDeclaredFields();//获取所有成员变量

                for (Field field : fields){
                    if (field.isAnnotationPresent(IgnoreField.class))
                        continue;
                    String value = resultSet.getString(field.getName());
                    field.setAccessible(true);
                    if(null != value)
                        field.set(bean, ConvertUtil.convert(value, field.getType()));//反射、给成员变量赋值
                }

                result.add(bean);
            }

        } catch (InstantiationException | IllegalAccessException e) {
            System.out.println("生成bean实例失败");
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
		//resultSet.close();
		resultSet = null;
        return result;
    }

}
