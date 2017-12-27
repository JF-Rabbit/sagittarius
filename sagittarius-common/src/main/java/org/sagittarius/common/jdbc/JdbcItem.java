package org.sagittarius.common.jdbc;

import java.sql.Connection;
import java.util.Arrays;

public class JdbcItem {

    private Connection connection;
    private String sql;
    private Object[] params;

    public JdbcItem() {
    }

    public JdbcItem(Connection connection, String sql, Object... params) {
        this.connection = connection;
        this.sql = sql;
        this.params = params;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object... params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "JdbcItem{" +
                "connection=" + connection +
                ", sql='" + sql + '\'' +
                ", params=" + Arrays.toString(params) +
                '}';
    }
}
