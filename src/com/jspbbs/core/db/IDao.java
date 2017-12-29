package com.jspbbs.core.db;

import java.util.List;

public interface IDao<T> {
    String getTableName();
    String getPK();

    Class<T> getTClass();

    StringBuffer buildWhere(String where);

    StringBuffer buildSql(String where);

    /**
     * such as find("age > ?",18)
     * @param where
     * @param paras
     * @return
     */
    List<T> find(String where, Object... paras);

    List<T> find(Condition condition);

    T findOne(Condition condition);

    /**
     * such as findOne("id", 1)
     * @param where
     * @param paras
     * @return
     */
    T findOne(String where, Object... paras);

    T findByID(Integer id);

    List<T> findAll();

    /**
     * such as delete("id", 1)
     * @param where
     * @param paras
     * @return 删除的数据条数
     */
    int delete(String where, Object... paras);

    int delete(Condition condition);

    boolean deleteByID(Integer id);

    /**
     * @param t
     * @return 插入成功返回更新后的对象，失败返回null
     */
    T insert(T t);

    /**
     * @param t
     * @return 更新成功返回新对象，失败返回null
     */
    T update(T t);

    long findCount();

    long findCount(String where, Object... paras);

    long findCount(Condition condition);

    Page<T> paginate(Page<T> page, String where, Object... paras);

    Page<T> paginate(Page<T> page, Condition condition);
}
