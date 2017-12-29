package com.jspbbs.core.util;

import com.sun.org.apache.regexp.internal.RE;
import com.sun.rowset.CachedRowSetImpl;
import org.junit.jupiter.api.Test;

import javax.sql.rowset.CachedRowSet;
import java.sql.*;
import java.util.List;

public class DBUtil {

    public static DBUtil me = new DBUtil();

    public DBUtil() {
        String driver = PropUtil.get("jdbc.driver");
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            System.out.println("无法正确加载数据库驱动");
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(PropUtil.get("jdbc.url")
                , PropUtil.get("jdbc.user"), PropUtil.get("jdbc.password"));
    }

    public int executeUpdate(String sql, Object[] paras){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);

            fillPreparedStatementParas(preparedStatement, paras);

            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection, preparedStatement);
        }
        return 0;
    }

    public ResultSet executeQuery(String sql, Object[] paras){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);

            fillPreparedStatementParas(preparedStatement, paras);

            ResultSet resultSet = preparedStatement.executeQuery();//查询结果

            CachedRowSet cachedRowSet = new CachedRowSetImpl();

            cachedRowSet.populate(resultSet);//转为CachedRowSet在Connection关闭后可继续使用

            return cachedRowSet;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection, preparedStatement);
        }
        return null;
    }

    public <T> List<T> queryBeanList(Class<T> clazz, String sql, Object[] paras){
        ResultSet resultSet = executeQuery(sql, paras);
        return InjectUtil.injectByResultSet(clazz, resultSet);
    }

    public Number queryNumber(String sql, Object[] paras){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Number result = 0;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);

            fillPreparedStatementParas(preparedStatement, paras);

            ResultSet resultSet = preparedStatement.executeQuery();//查询结果

            if(resultSet.next()){
                result = resultSet.getBigDecimal(1);
            }

            return result;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection, preparedStatement);
        }
        return result;
    }

    public int queryInt(String sql, Object[] paras){
        return queryNumber(sql, paras).intValue();
    }

    public long queryLong(String sql, Object[] paras){
        return queryNumber(sql, paras).longValue();
    }

    public double queryDouble(String sql, Object[] paras){
        return queryNumber(sql, paras).doubleValue();
    }

    public float queryFloat(String sql, Object[] paras){
        return queryNumber(sql, paras).floatValue();
    }

    private void fillPreparedStatementParas(PreparedStatement preparedStatement, Object[] paras) throws SQLException {
        if (null == paras)
            return;
        for (int i = 0; i < paras.length; i++){
            preparedStatement.setObject(i + 1, paras[i]);
        }
    }

    private void close(Connection connection, PreparedStatement preparedStatement){

        try {
            if (null != connection)
                connection.close();

            if (null != preparedStatement){
                preparedStatement.close();
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Test
    public void testQueryInt(){
//        String sql = "SELECT COUNT(*) FROM bbs_user WHERE username=?";
//        int count = DBUtil.me.queryNumber(sql, new Object[]{"admi"}).intValue();
//        assert count !=0;
    }
}
