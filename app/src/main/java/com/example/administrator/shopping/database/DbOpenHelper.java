package com.example.administrator.shopping.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

//sett 商品详情 支付成功  （灰色） 弹窗 直接跳转   TOast提示中间  评论 角标  商品详情页面  单位  多用户登录
//商品数  星好评  各种评价
public class DbOpenHelper {

    /*全局静态 */
    private static final String DRIVERS = "com.mysql.jdbc.Driver";
    // private static final String URL = "jdbc:mysql://172.30.243.110:3306/bishe?useUnicode=true&characterEncoding=utf8";
    private static final String URL = "jdbc:mysql://172.29.90.112:3306/bishe?useUnicode=true&characterEncoding=utf8";
    private static final String USER = "cys";
    private static final String PWD = "123456";

    public static Connection conn;   // 连接对象
    public static Statement stmt;    // 命令集
    public static PreparedStatement pStmt;   // 预编译命令集
    public static ResultSet rs;     // 结果集

    // 连接方法
    public static void getConnection() {
        try {
            Class.forName(DRIVERS);
            conn = DriverManager.getConnection(URL, USER, PWD);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // 关闭数据库操作对象，为了数据库的安全
    public static void closeAll() {
        try {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (stmt != null) {
                stmt.close();
                stmt = null;
            }
            if (pStmt != null) {
                pStmt.close();
                pStmt = null;
            }
            if (conn != null) {
                conn.close();
                conn = null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
