package atomdu.tool.tanslate.utils;

import org.junit.After;
import org.sqlite.javax.SQLiteConnectionPoolDataSource;

import java.sql.*;

/**
 * Created by dzw on 17-6-11.
 */
public class SQLiteUtils {
    static String driver = "com.mysql.jdbc.Driver";
    static String dbName = "atomdu";
    static String userName = "root";

    // 创建表
    private Connection conn;

    public void init() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        SQLiteConnectionPoolDataSource source = new SQLiteConnectionPoolDataSource();
        conn = source.getPooledConnection().getConnection();
    }

    public void insert(String from, String to) throws SQLException {
        Statement stmt = null;
        stmt = conn.createStatement();

        // 执行DDL语句，删除表
        //stmt.execute("DROP TABLE IF EXISTS word");
        // 创建表
        String sql_create_word_table = "CREATE TABLE IF NOT EXISTS word "
                + "(from CHAR(50) PRIMARY KEY, "
                + "to CHAR(50)) ";
        stmt.execute(sql_create_word_table);

        // 执行DML语句，插入记录
        stmt.execute("INSERT INTO COMPANY (from ,to) VALUES ('" + from + "' ,'" + to + "')");
        if (stmt != null && !stmt.isClosed()) stmt.close();
        if (conn != null && !conn.isClosed()) conn.close();
    }

    public String getTo(String from) throws SQLException {
        Statement stmt = null;
        stmt = conn.createStatement();

        // 执行DDL语句，删除表
        //stmt.execute("DROP TABLE IF EXISTS word");
        // 创建表
        String sql_create_word_table = "CREATE TABLE IF NOT EXISTS word "
                + "(from CHAR(50) PRIMARY KEY, "
                + "to CHAR(50)) "
                + "DEFAULT CHARSET=utf8 ";
        stmt.execute(sql_create_word_table);
        // 执行DQL语句，查询数据
        ResultSet rs = stmt.executeQuery("SELECT * FROM word WHERE from=" + from);
        //Assert.assertTrue(rs.next());
        String to = "";
        if (rs.next()) {
            to = rs.getString(2);
            System.out.println(rs.getString(1) + "\t"
                    + rs.getString(2));
        }
        if (rs != null && !rs.isClosed()) rs.close();
        if (stmt != null && !stmt.isClosed()) stmt.close();
        if (conn != null && !conn.isClosed()) conn.close();

        return to;
    }

    @After
    public void destroy() throws SQLException {
        if (conn != null) conn.close();
    }

//    public void insert(String from, String to) throws SQLException {
//        Statement stmt = null;
//        stmt = conn.createStatement();
//
//        // 执行DDL语句，删除表
//        //stmt.execute("DROP TABLE IF EXISTS word");
//        // 创建表
//        String sql_create_word_table = "CREATE TABLE IF NOT EXISTS word "
//                + "(from CHAR(50) PRIMARY KEY, "
//                + "to CHAR(50)) ";
//        stmt.execute(sql);
//
//        // 执行DML语句，插入记录
//        stmt.execute("INSERT INTO COMPANY (from ,to) VALUES ('" + from + "' ,'" + to + "')");
//
//        // 执行DQL语句，查询数据
//        ResultSet rs = stmt.executeQuery("SELECT * FROM COMPANY ");
//        Assert.assertTrue(rs.next());
//        // 1	zlikun	28	上海	2400
//        System.out.println(rs.getLong(1) + "\t"
//                + rs.getString(2) + "\t"
//                + rs.getInt(3) + "\t"
//                + rs.getString(4) + "\t"
//                + rs.getInt(5));
//
//        if (rs != null && !rs.isClosed()) rs.close();
//        if (stmt != null && !stmt.isClosed()) stmt.close();
//        if (conn != null && !conn.isClosed()) conn.close();
//    }
}
