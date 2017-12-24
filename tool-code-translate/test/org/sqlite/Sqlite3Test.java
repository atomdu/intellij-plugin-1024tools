package org.sqlite;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.sqlite.javax.SQLiteConnectionPoolDataSource;

import java.sql.*;

/**
 * Created by atomdu on 2017/12/9.
 */
public class Sqlite3Test {
    private Connection conn;

    @Before
    public void init() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        SQLiteConnectionPoolDataSource source = new SQLiteConnectionPoolDataSource();
        conn = source.getPooledConnection().getConnection();
    }

    @Test
    public void test() throws SQLException {
        Statement stmt = null;
        stmt = conn.createStatement();

        // 执行DDL语句，删除表
        stmt.execute("DROP TABLE IF EXISTS COMPANY");

        // 创建表
        String sql = "CREATE TABLE COMPANY "
                + "(ID INT PRIMARY KEY,"
                + "NAME TEXT NOT NULL, "
                + "AGE INT NOT NULL, "
                + "ADDRESS CHAR(50), "
                + "SALARY INT)";
        stmt.execute(sql);

        // 执行DML语句，插入记录
        stmt.execute("INSERT INTO COMPANY (ID ,NAME ,AGE ,ADDRESS ,SALARY) VALUES (1 ,'zlikun' ,28 ,'上海' ,2400)");

        // 执行DQL语句，查询数据
        ResultSet rs = stmt.executeQuery("SELECT * FROM COMPANY WHERE ID = 1");
        Assert.assertTrue(rs.next());
        // 1	zlikun	28	上海	2400
        System.out.println(rs.getLong(1) + "\t"
                + rs.getString(2) + "\t"
                + rs.getInt(3) + "\t"
                + rs.getString(4) + "\t"
                + rs.getInt(5));

        if (rs != null && !rs.isClosed()) rs.close();
        if (stmt != null && !stmt.isClosed()) stmt.close();
        if (conn != null && !conn.isClosed()) conn.close();
    }

    @After
    public void destroy() throws SQLException {
        if (conn != null) conn.close();
    }
}
