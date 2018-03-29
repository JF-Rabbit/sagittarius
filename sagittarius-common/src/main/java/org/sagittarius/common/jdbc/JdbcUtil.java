package org.sagittarius.common.jdbc;

import org.sagittarius.common.ReflectUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

public class JdbcUtil {

    private static final Logger logger = LoggerFactory.getLogger(JdbcUtil.class);

    /**
     * 获取Connection
     */
    public static Connection getConnection(String url) throws SQLException {
        return DriverManager.getConnection(url);
    }

    /**
     * 关闭Connection
     */
    public static void close(Connection conn) throws SQLException {
        if (conn != null) {
            logger.info("Close Connection");
            conn.close();
        }
    }

    private static PreparedStatement prepare(Connection conn, String sql, Object... params) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            ps.setObject(i + 1, params[i]);
        }
        logger.info("SQL: {}", ps.toString().substring(ps.toString().indexOf(":") + 1));
        return ps;
    }

    public enum JdbcActionEnum {
        EXECUTE, QUERY, CHANGE
    }

    /**
     * 根据JdbcActionEnum执行
     */
    public static void execute(Connection conn, String sql, JdbcActionEnum actionEnum, Object... params) throws SQLException {
        switch (actionEnum) {
            case EXECUTE:
                execute(conn, sql, params);
                break;
            case QUERY:
                query(conn, sql, params);
                break;
            case CHANGE:
                change(conn, sql, params);
                break;
            default:
                break;
        }
    }

    /**
     * 执行
     */
    public static boolean execute(Connection conn, String sql, Object... params) throws SQLException {
        return prepare(conn, sql, params).execute();
    }

    /**
     * 增、删、改
     */
    public static int change(Connection conn, String sql, Object... params) throws SQLException {
        return prepare(conn, sql, params).executeUpdate();
    }

    /**
     * 查询
     */
    public static ResultSet query(Connection conn, String sql, Object... params) throws SQLException {
        return prepare(conn, sql, params).executeQuery();
    }

    /**
     * ResultSet转Map
     */
    public static Map<String, Object> resultSet2Map(ResultSet rs, Map<String, Object> map, Set<String> notFindSet) {
        map.forEach((k, v) -> {
            try {
                map.put(k, rs.getObject(k));
            } catch (SQLException e) {
                notFindSet.add(k);
            }
        });
        return map;
    }

    /**
     * ResultSet转Object
     */
    public static <T> T resultSet2Object(ResultSet rs, T object, Set<String> notFindSet) {
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                ReflectUnit.setField(field, object, rs.getObject(field.getName()));
            } catch (SQLException e) {
                notFindSet.add(field.getName());
            }
        }
        return object;
    }

    /**
     * 获取ResultSet并转成Map列表
     */
    public static List<Map<String, Object>> getMap(JdbcItem item, Map<String, Object> map) throws SQLException {
        List<Map<String, Object>> mapList = new ArrayList<>();
        ResultSet rs = query(item.getConnection(), item.getSql(), item.getParams());
        Set<String> notFindSet = new HashSet<>();
        while (rs.next()) {
            Map<String, Object> tmp = new HashMap<>();
            tmp.putAll(resultSet2Map(rs, map, notFindSet));
            mapList.add(tmp);
        }
        if (notFindSet.size() > 0) {
            logger.warn("Not find keys: {} in ResultSet", notFindSet);
        }
        return mapList;
    }

    /**
     * 获取ResultSet并转成对象列表
     */
    public static <T> List<T> getObject(JdbcItem item, T object) throws SQLException {
        List<T> objectList = new ArrayList<>();
        ResultSet rs = query(item.getConnection(), item.getSql(), item.getParams());
        Set<String> notFindSet = new HashSet<>();
        while (rs.next()) {
            objectList.add(resultSet2Object(rs, object, notFindSet));
        }
        logger.warn("Not find keys: {} in ResultSet", notFindSet);
        return objectList;
    }
}
