package org.sagittarius.common.test;

import org.sagittarius.common.jdbc.JdbcItem;
import org.sagittarius.common.jdbc.JdbcUtil;
import org.sagittarius.common.test.entity.Foo;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class TestJdbcUtil {

    Connection conn = JdbcUtil.getConnection(
            "jdbc:mysql://localhost:3306/mydb?" +
                    "user=root&password=aa123456" +
                    "&autoReconnect=true&useSSL=false" +
                    "&useUnicode=true&characterEncoding=utf8");

    public TestJdbcUtil() throws SQLException {
    }

    @Test
    public void update() throws SQLException {
        String sql = "update foo set foo_name='Marry1' where foo_name='Marry0'";
        int result = JdbcUtil.update(conn, sql);
        System.out.println(result);
    }

    @Test(enabled = true)
    public void select() throws SQLException {
        Map<String, Object> map = new HashMap<>();
        map.put("foo_id", null);
        map.put("foo_name", null);
        map.put("foo_something1", null);
        map.put("foo_something2", null);
        Foo f = new Foo();

        JdbcItem item1 = new JdbcItem(conn, "select * from foo");
        JdbcItem item2 = new JdbcItem(conn, "select * from foo where foo_id=? and foo_name=?", 1, "qwert");

        System.out.println(JdbcUtil.getMap(item1, map));
        System.out.println(JdbcUtil.getObject(item1, f));
        System.out.println(JdbcUtil.getMap(item2, map));
        System.out.println(JdbcUtil.getObject(item2, f));
    }

    @AfterClass
    public void close() throws SQLException {
        JdbcUtil.close(conn);
    }

}
