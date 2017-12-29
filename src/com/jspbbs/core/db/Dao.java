package com.jspbbs.core.db;

import com.jspbbs.core.annotation.IgnoreField;
import com.jspbbs.core.annotation.PrimaryKey;
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

public class Dao<T> implements IDao<T> {

    private Class<T> tClass = null;
    private String tableName = null;
    private String primaryKey = null;
    private Field pkField = null;

    private Field getPkField(){
        if(null != pkField)
            return pkField;
        Field[] fields = getTClass().getDeclaredFields();
        for (Field field : fields){
            if (field.isAnnotationPresent(PrimaryKey.class)){
                pkField = field;
                break;
            }
        }
        pkField.setAccessible(true);
        return pkField;
    }

    public String getPK(){
        if (null != primaryKey)
            return primaryKey;

        if (getPkField().isAnnotationPresent(PrimaryKey.class)){
            PrimaryKey pk = getPkField().getAnnotation(PrimaryKey.class);
            primaryKey = pk.value();
            if ("".equals(primaryKey))
                primaryKey = getPkField().getName();//如果没有指定主键名则以注解的成员变量名作为主键名
        } else {
            primaryKey = "id";//默认为id
        }

        return primaryKey;
    }

    public String getTableName(){
        if (null != tableName)
            return tableName;
        if (getTClass().isAnnotationPresent(Table.class)){
            Table table = getTClass().getAnnotation(Table.class);
            tableName = table.value();
        } else {
            tableName = getTClass().getSimpleName().toLowerCase();
        }

        return tableName;
    }

    public Class<T> getTClass(){
        if (null == tClass){
            tClass = ReflectUtil.getTypeClass(getClass());
        }

        return tClass;
    }

    public void settClass(Class<T> clazz){
        this.tClass = clazz;
    }

    public StringBuffer buildWhere(String where){
        StringBuffer sqlStr = new StringBuffer();
        if (null != where && !"".equals(where)){
            if(where.startsWith(" ORDER BY ")){
                sqlStr.append(where);
                return sqlStr;
            }
            sqlStr.append(" WHERE ")
                    .append(where);
        }

        return sqlStr;
    }

    public StringBuffer buildSql(String where){
        StringBuffer sqlStr = new StringBuffer("SELECT * FROM ");
        sqlStr.append(getTableName())
                .append(buildWhere(where));

        return sqlStr;
    }

    /**
     * such as find("age > ?",18)
     * @param where
     * @param paras
     * @return
     */
    public List<T> find(String where, Object... paras){
        StringBuffer sqlStr = buildSql(where);
        List<T> list = DBUtil.me.queryBeanList(getTClass(), sqlStr.toString(), paras);
        return list;
    }

    public List<T> find(Condition condition){
        if (null == condition){
            condition = new Condition();//避免参数为空
        }

        return find(condition.getWhere().toString(), condition.getParas());
    }

    public T findOne(Condition condition){
        if (null == condition){
            condition = new Condition();//避免参数为空
        }
        return findOne(condition.getWhere().toString(), condition.getParas());
    }

    /**
     * such as findOne("id", 1)
     * @param where
     * @param paras
     * @return
     */
    public T findOne(String where, Object... paras) {
        StringBuffer sqlStr = buildSql(where);
        sqlStr.append(" limit 1");
        List<T> list = DBUtil.me.queryBeanList(getTClass(), sqlStr.toString(), paras);
        return list.isEmpty() ? null : list.get(0);
    }

    public T findByID(Integer id){
        return findOne("`"+ getPK() +"`=?", id);
    }

    @Override
    public List<T> findAll() {
        return find("");
    }

    /**
     * such as delete("id", 1)
     * @param where
     * @param paras
     * @return 删除的数据条数
     */
    public int delete(String where, Object... paras){
        StringBuffer sqlStr = new StringBuffer("DELETE FROM ");
        sqlStr.append(getTableName())
                .append(buildWhere(where));

        return DBUtil.me.executeUpdate(sqlStr.toString(), paras);
    }

    public int delete(Condition condition){
        if (null == condition){
            condition = new Condition();//避免参数为空
        }

        return delete(condition.getWhere().toString(), condition.getParas());
    }

    public boolean deleteByID(Integer id){
        return delete("`"+ getPK() +"`=?", id) > 0;
    }

    /**
     * @param t
     * @return 插入成功返回更新后的对象，失败返回null
     */
    public T insert(T t){
        Field[] fields = getTClass().getDeclaredFields();
        StringBuffer sqlStr = new StringBuffer("INSERT INTO `").append(getTableName()).append("` (");
        StringBuffer valueStr = new StringBuffer(" VALUES(");
        List<Object> paraList = new ArrayList<>();

        int fieldsLen = fields.length;
        for (int i = 0; i < fieldsLen; i++){
            Field field = fields[i];
            field.setAccessible(true);

            //忽略的成员变量（处理各种关系）
            IgnoreField IgnoreField = field.getAnnotation(IgnoreField.class);
            if (null != IgnoreField){
                continue;
            }

            if (field == getPkField()){//跳过主键
                continue;
            }

            sqlStr.append('`').append(field.getName()).append('`');
            valueStr.append('?');

            if(i < fieldsLen - 1){
                sqlStr.append(',');
                valueStr.append(',');
            }

            try {
                paraList.add(field.get(t));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        sqlStr.append(')').append(valueStr).append(')');

        int effectRow = DBUtil.me.executeUpdate(sqlStr.toString(), paraList.toArray());

        if (effectRow > 0){
            return findOne(new Condition().desc("id"));
        }

        return null;
    }

    /**
     * @param t
     * @return 更新成功返回新对象，失败返回null
     */
    public T update(T t){
        Field[] fields = getTClass().getDeclaredFields();
        StringBuffer sqlStr = new StringBuffer("UPDATE ").append(getTableName()).append(" SET ");
        List<Object> paraList = new ArrayList<>();

        int fieldsLen = fields.length;
        for (int i = 0; i < fieldsLen; i++){

            Field field = fields[i];
            field.setAccessible(true);

            //忽略的成员变量（处理各种关系）
            IgnoreField IgnoreField = field.getAnnotation(IgnoreField.class);
            if (null != IgnoreField){
                continue;
            }

            if (field == getPkField()){//跳过主键
                continue;
            }

            sqlStr.append('`').append(field.getName()).append("` = ?");

            if(i < fieldsLen - 1){
                sqlStr.append(',');
            }

            try {
                paraList.add(field.get(t));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        sqlStr.append(" WHERE `").append(getPK()).append("` = ?");

        Object primaryKey = null;

        try {
            primaryKey = getPkField().get(t);
            paraList.add(primaryKey);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        if (DBUtil.me.executeUpdate(sqlStr.toString(), paraList.toArray()) > 0){
            return  findOne(Condition.create(getPK(), "=", primaryKey));
        }

        return null;
    }

    public long findCount(){
        return findCount("");
    }

    public long findCount(String where, Object... paras){
        StringBuffer sqlStr = new StringBuffer("SELECT COUNT(*) FROM ");
        sqlStr.append(getTableName())
                .append(buildWhere(where));
        return DBUtil.me.queryLong(sqlStr.toString(), paras);
    }

    public long findCount(Condition condition){
        if (null == condition)
            condition = new Condition();
        return findCount(condition.getWhere().toString(), condition.getParas());
    }

    public Page<T> paginate(Page<T> page, String where, Object... paras){

        int pageNumber = page.getPageNumber();
        int pageSize = page.getPageSize();

        if(null == page || pageSize == 0){
            return null;
        }

        //获取数据总量及总页数
        long totalRow = findCount(where, paras);
        long totalPage = totalRow / pageSize;

        if(totalRow % pageSize > 0)
            totalPage++;

        page.setTotalPage(totalPage);
        page.setTotalRow(totalRow);

        String sql = buildSql(where).append(" LIMIT ? OFFSET ?").toString();

        //添加分页参数
        List<Object> paramList = new ArrayList<Object>(Arrays.asList(paras));

        paramList.add(pageSize);
        paramList.add(pageSize * (pageNumber - 1));

        List<T> list = DBUtil.me.queryBeanList(getTClass(), sql , paramList.toArray() );
        page.setList(list);

        return page;
    }

    public Page<T> paginate(Page<T> page, Condition condition){

        if(null == page){
            return null;
        }

        if (null == condition)
            condition = new Condition();

        return paginate(page, condition.getWhere().toString(), condition.getParas());
    }

}
