package org.wulizi.myssm.helper;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wulizi.myssm.util.PropsUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author wulizi
 */
public class DatabaseHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(
            DatabaseHelper.class);

    private final static ThreadLocal<Connection> CONNECTION_HOLDER =
            new ThreadLocal<>();

    private static final QueryRunner QUERY_RUNNER = new QueryRunner();

    private static final BasicDataSource DATA_SOURCE;
    private final static String DRIVER;
    private final static String URL;
    private final static String USERNAME;
    private final static String PASSWORD;

    static {
        Properties props = PropsUtil.loadProps("dbconfig.properties");
        DRIVER = PropsUtil.getString(props, "jdbc.driver");
        URL = PropsUtil.getString(props, "jdbc.url");
        USERNAME = PropsUtil.getString(props, "jdbc.username");
        PASSWORD = PropsUtil.getString(props, "jdbc.password");
        DATA_SOURCE = new BasicDataSource();

        DATA_SOURCE.setDriverClassName(DRIVER);
        DATA_SOURCE.setUrl(URL);
        DATA_SOURCE.setUsername(USERNAME);
        DATA_SOURCE.setPassword(PASSWORD);
    }

    public static Connection getConnection() {
        Connection conn = CONNECTION_HOLDER.get();
        if (conn == null) {
            try {
                conn = DATA_SOURCE.getConnection();
            } catch (SQLException e) {
                LOGGER.error("获取连接错误", e);
                throw new RuntimeException(e);
            }
            CONNECTION_HOLDER.set(conn);
        }
        return conn;
    }

    /**
     * 开始事务
     */
    public static void beginTransaction() {
        Connection conn = getConnection();
        if (conn != null) {
            try {
                conn.setAutoCommit(false);
            } catch (SQLException e) {
                LOGGER.error("错误，打开事务错误",e);
                throw new RuntimeException(e);
            }finally {
                CONNECTION_HOLDER.set(conn);
            }
        }
    }

    /**
     * 提交事务
     */
    public static void commitTransaction() {
        Connection conn = getConnection();
        try {
            if (conn != null) {
                conn.commit();
                conn.close();
            }
        } catch (SQLException e) {
            LOGGER.error("提交事务错误",e);
            throw new RuntimeException(e);
        }finally {
            CONNECTION_HOLDER.remove();
        }
    }

    /**
     * 回滚事务
     */
    public static void rollbackTransaction() {
        Connection conn = getConnection();
        try {
            if (conn != null) {
                conn.rollback();
                conn.close();
            }
        } catch (SQLException e) {
            LOGGER.error("回滚事务错误",e);
            throw new RuntimeException(e);
        }finally {
            CONNECTION_HOLDER.remove();
        }
    }
    /**
     * 查询实体类
     */
    public static <T> List<T> queryEntryList(Class<T> entryClass, String sql,
                                             Object... params) {
        List<T> entryList;
        try {
            Connection connection = getConnection();
            entryList = QUERY_RUNNER.query(connection, sql, new BeanListHandler<T>(entryClass),
                    params);
        } catch (SQLException e) {
            LOGGER.error("查询错误", e);
            throw new RuntimeException(e);
        }
        return entryList;
    }

    public static List<Map<String, Object>> executeQuery(String sql, Object... params) {
        List<Map<String, Object>> result;
        try {
            Connection connection = getConnection();
            result = QUERY_RUNNER.query(connection, sql, new MapListHandler(), params);
        } catch (SQLException e) {
            LOGGER.error("查询错误", e);
            throw new RuntimeException(e);
        }
        return result;
    }


    /**
     * 修改表
     */
    public static int executeUpdate(String sql, Object... params) {
        int rows = 0;
        try {
            Connection connection = getConnection();
            rows = QUERY_RUNNER.update(connection, sql, params);
        } catch (SQLException e) {
            LOGGER.error("修改错误", e);
            throw new RuntimeException(e);
        }
        return rows;
    }

    /**
     * 插入实体
     */
    public static <T> boolean insertEntity(Class<T> entryClass, Map<String, Object> fieldMap) {
        if (MapUtils.isEmpty(fieldMap)) {
            LOGGER.error("字段为空，不能插入");
            return false;
        }

        String sql = "INSERT INTO " + getEntryName(entryClass);
        StringBuilder columns = new StringBuilder("(");
        StringBuilder values = new StringBuilder("(");
        for (String filedName : fieldMap.keySet()) {
            columns.append(filedName).append(", ");
            values.append("?, ");
        }
        columns.replace(columns.lastIndexOf(", "), columns.length(), ")");
        values.replace(values.lastIndexOf(", "), values.length(), ")");
        sql = sql + columns + " VALUES " + values;

        Object[] params = fieldMap.values().toArray();

        return executeUpdate(sql, params) == 1;
    }


    /**
     *修改实体
     */
    public static <T> boolean updateEntity(Class<T> entryClass, long id, Map<String, Object> fieldMap) {
        if (MapUtils.isEmpty(fieldMap)) {
            LOGGER.error("字段为空，不能跟新");
            return false;
        }

        String sql = "UPDATE " + getEntryName(entryClass) + " SET ";
        StringBuilder columns = new StringBuilder();
        for (String fieldName : fieldMap.keySet()) {
            columns.append(fieldName).append("=?, ");
        }
        sql = sql + columns.substring(0, columns.lastIndexOf(", ")) + "WHERE id = ?";
        List<Object> paramsList = new ArrayList<>();
        paramsList.addAll(fieldMap.values());
        paramsList.add(id);
        Object[] params = paramsList.toArray();
        return executeUpdate(sql,params) == 1;
    }

    /**
     * 删除实体
     */
    public static <T> boolean deleteEntity(Class<T> entryClass, long id) {
        String sql = "DELETE FROM "+ getEntryName(entryClass)+ " where id = ?";
        return executeUpdate(sql,id) == 1;
    }
    private static <T> String getEntryName(Class<T> entryClass) {
        return entryClass.getSimpleName();
    }

}
