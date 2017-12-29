package com.jspbbs.core.db;

import com.jspbbs.core.annotation.IgnoreField;
import com.jspbbs.core.annotation.PrimaryKey;
import com.jspbbs.core.annotation.Table;
import com.jspbbs.core.util.DBUtil;
import com.jspbbs.core.util.ReflectUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class CommonDao{

    private static Map<Class, Field> primaryKeyFieldMap = new ConcurrentHashMap<>();
    private static Map<Class, String> primaryKeyMap = new ConcurrentHashMap<>();
    private static Map<Class, String> tableNameMap = new ConcurrentHashMap<>();

    private static CommonDao instance = null;

    public static CommonDao getInstance() {
        if (null == instance){
            synchronized (CommonDao.class){
                if (null == instance)
                    instance = new CommonDao();
            }
        }
        return instance;
    }

    private <T> Field getPkField(Class<T> clazz){

        if(primaryKeyFieldMap.containsKey(clazz))
            return primaryKeyFieldMap.get(clazz);

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields){
            if (field.isAnnotationPresent(PrimaryKey.class)){
                field.setAccessible(true);
                primaryKeyFieldMap.put(clazz, field);
                return field;
            }
        }
        return null;
    }

    public <T> String getPK(Class<T> clazz){

        if(primaryKeyMap.containsKey(clazz))
            return primaryKeyMap.get(clazz);

        String primaryKey = "id";//默认为id
        Field pkField = getPkField(clazz);
        if (null == pkField)
            return primaryKey;
        if (pkField.isAnnotationPresent(PrimaryKey.class)){
            PrimaryKey pk = pkField.getAnnotation(PrimaryKey.class);
            primaryKey = pk.value();
            if ("".equals(primaryKey))
                primaryKey = pkField.getName();//如果没有指定主键名则以注解的成员变量名作为主键名
            primaryKeyMap.put(clazz, primaryKey);
        }

        return primaryKey;
    }

    public <T> String getTableName(Class<T> clazz){

        if(tableNameMap.containsKey(clazz))
            return tableNameMap.get(clazz);

        String tableName;
        if (clazz.isAnnotationPresent(Table.class)){
            Table table = clazz.getAnnotation(Table.class);
            tableName = table.value();
        } else {
            tableName = clazz.getSimpleName().toLowerCase();
        }

        tableNameMap.put(clazz, tableName);

        return tableName;
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

    public <T> StringBuffer buildSql(Class<T> clazz, String where){
        StringBuffer sqlStr = new StringBuffer("SELECT * FROM ");
        sqlStr.append(getTableName(clazz))
                .append(buildWhere(where));

        return sqlStr;
    }

    /**
     * such as find("age > ?",18)
     * @param where
     * @param paras
     * @return
     */
    public <T> List<T> find(Class<T> clazz, String where, Object... paras){
        StringBuffer sqlStr = buildSql(clazz,where);
        List<T> list = DBUtil.me.queryBeanList(clazz, sqlStr.toString(), paras);
        return list;
    }

    public <T> List<T> find(Class<T> clazz, Condition condition){
        if (null == condition){
            condition = new Condition();//避免参数为空
        }

        return find(clazz, condition.getWhere().toString(), condition.getParas());
    }

    public <T> T findOne(Class<T> clazz, Condition condition){
        if (null == condition){
            condition = new Condition();//避免参数为空
        }
        return findOne(clazz, condition.getWhere().toString(), condition.getParas());
    }

    /**
     * such as findOne("id", 1)
     * @param where
     * @param paras
     * @return
     */
    public <T> T findOne(Class<T> clazz, String where, Object... paras) {
        StringBuffer sqlStr = buildSql(clazz, where);
        sqlStr.append(" limit 1");
        List<T> list = DBUtil.me.queryBeanList(clazz, sqlStr.toString(), paras);
        return list.isEmpty() ? null : list.get(0);
    }

    public <T> T findByID(Class<T> clazz, Integer id){
        return findOne(clazz, "`"+ getPK(clazz) +"`=?", id);
    }

    public <T> List<T> findAll(Class<T> clazz) {
        return find(clazz, "");
    }

    /**
     * such as delete("id", 1)
     * @param where
     * @param paras
     * @return 删除的数据条数
     */
    public <T> int delete(Class<T> clazz, String where, Object... paras){
        StringBuffer sqlStr = new StringBuffer("DELETE FROM ");
        sqlStr.append(getTableName(clazz))
                .append(buildWhere(where));

        return DBUtil.me.executeUpdate(sqlStr.toString(), paras);
    }

    public <T> int delete(Class<T> clazz, Condition condition){
        if (null == condition){
            condition = new Condition();//避免参数为空
        }

        return delete(clazz, condition.getWhere().toString(), condition.getParas());
    }

    public <T> boolean deleteByID(Class<T> clazz, Integer id){
        return delete(clazz, "`"+ getPK(clazz) +"`=?", id) > 0;
    }

    /**
     * @param t
     * @return 插入成功返回更新后的对象，失败返回null
     */
    public <T> T insert(Class<T> clazz, T t){
        Field[] fields = clazz.getDeclaredFields();
        StringBuffer sqlStr = new StringBuffer("INSERT INTO `").append(getTableName(clazz)).append("` (");
        StringBuffer valueStr = new StringBuffer(" VALUES(");
        List<Object> paraList = new ArrayList<>();
        int fieldsLen = fields.length;
        boolean isFirstField = true;
        for (int i = 0; i < fieldsLen; i++){
            Field field = fields[i];
            field.setAccessible(true);

            //忽略的成员变量（处理各种关系）
            IgnoreField IgnoreField = field.getAnnotation(IgnoreField.class);
            if (null != IgnoreField){
                continue;
            }

            if (field.getName().equals(getPK(clazz))){//跳过主键
                continue;
            }

            if(!isFirstField){
                sqlStr.append(',');
                valueStr.append(',');
            }

            isFirstField = false;

            sqlStr.append('`').append(field.getName()).append('`');
            valueStr.append('?');

            try {
                paraList.add(field.get(t));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        sqlStr.append(')').append(valueStr).append(')');

        int effectRow = DBUtil.me.executeUpdate(sqlStr.toString(), paraList.toArray());

        if (effectRow > 0){
            if (null != getPkField(clazz))
                return findOne(clazz, new Condition().desc(getPK(clazz)));
            return findOne(clazz, "");
        }

        return null;
    }

    public <T> int insertBatch(Class<T> clazz, List<T> list){
        int effectRow = 0;
        for (T t : list){
            if (null != insert(clazz, t))
                effectRow++;
        }

        return effectRow;
    }

    /**
     * @param t
     * @return 更新成功返回新对象，失败返回null
     */
    public <T> T update(Class<T> clazz, T t){
        Field[] fields = clazz.getDeclaredFields();
        StringBuffer sqlStr = new StringBuffer("UPDATE ").append(getTableName(clazz)).append(" SET ");
        List<Object> paraList = new ArrayList<>();

        int fieldsLen = fields.length;
        boolean isFirstField = true;

        for (int i = 0; i < fieldsLen; i++){

            Field field = fields[i];
            field.setAccessible(true);

            //忽略的成员变量（处理各种关系）
            IgnoreField IgnoreField = field.getAnnotation(IgnoreField.class);
            if (null != IgnoreField){
                continue;
            }

            if (field == getPkField(clazz)){//跳过主键
                continue;
            }

            if(!isFirstField){
                sqlStr.append(',');
            }

            isFirstField = false;

            sqlStr.append('`').append(field.getName()).append("` = ?");

            try {
                paraList.add(field.get(t));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        sqlStr.append(" WHERE `").append(getPK(clazz)).append("` = ?");

        Object primaryKey = null;

        try {
            primaryKey = getPkField(clazz).get(t);
            paraList.add(primaryKey);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        if (DBUtil.me.executeUpdate(sqlStr.toString(), paraList.toArray()) > 0){
            return  findOne(clazz, Condition.create(getPK(clazz), "=", primaryKey));
        }

        return null;
    }

    public <T> long findCount(Class<T> clazz){
        return findCount(clazz, "");
    }

    public <T> long findCount(Class<T> clazz, String where, Object... paras){
        StringBuffer sqlStr = new StringBuffer("SELECT COUNT(*) FROM ");
        sqlStr.append(getTableName(clazz))
                .append(buildWhere(where));
        return DBUtil.me.queryLong(sqlStr.toString(), paras);
    }

    public <T> long findCount(Class<T> clazz, Condition condition){
        if (null == condition)
            condition = new Condition();
        return findCount(clazz, condition.getWhere().toString(), condition.getParas());
    }

    public <T> Page<T> paginate(Class<T> clazz, Page<T> page, String where, Object... paras){

        int pageNumber = page.getPageNumber();
        int pageSize = page.getPageSize();

        if(null == page || pageSize == 0){
            return null;
        }

        //获取数据总量及总页数
        long totalRow = findCount(clazz, where, paras);
        long totalPage = totalRow / pageSize;

        if(totalRow % pageSize > 0)
            totalPage++;

        page.setTotalPage(totalPage);
        page.setTotalRow(totalRow);

        String sql = buildSql(clazz, where).append(" LIMIT ? OFFSET ?").toString();

        //添加分页参数
        List<Object> paramList = new ArrayList<Object>(Arrays.asList(paras));

        paramList.add(pageSize);
        paramList.add(pageSize * (pageNumber - 1));

        List<T> list = DBUtil.me.queryBeanList(clazz, sql , paramList.toArray() );
        page.setList(list);

        return page;
    }

    public <T> Page<T> paginate(Class<T> clazz, Page<T> page, Condition condition){

        if(null == page){
            return null;
        }

        if (null == condition)
            condition = new Condition();

        return paginate(clazz, page, condition.getWhere().toString(), condition.getParas());
    }

}
