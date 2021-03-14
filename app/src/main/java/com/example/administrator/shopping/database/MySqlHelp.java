package com.example.administrator.shopping.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MySqlHelp {

    public static int conncetMysql() {  // 连接数据库
        final String CLS = "com.mysql.jdbc.Driver";
        final String URL = "jdbc:mysql://172.30.243.110:3306/bishe?useUnicode=true&characterEncoding=utf8";
        final String USER = "root";
        final String PWD = "123456";

        try {
            Class.forName(CLS);
            Connection conn = DriverManager.getConnection(URL, USER, PWD);
            Statement stmt = conn.createStatement();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }
}
/*
        废弃
        int name=0;

        try{
        Class.forName(CLS);
        Connection conn=DriverManager.getConnection(URL,USER,PWD);
        String sql = "select name from entityuser";
        Statement stmt=conn.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        while(rs.next()){
        name=rs.getInt("name");
        }

        }catch(Exception ex){
        ex.printStackTrace();
        }
        return 0;
        }
        }
*/
