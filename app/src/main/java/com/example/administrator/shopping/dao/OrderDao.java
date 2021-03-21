package com.example.administrator.shopping.dao;

import com.example.administrator.shopping.database.DbOpenHelper;

public class OrderDao extends DbOpenHelper {


    public static int insOrder(String goodsCount, String goodsPrice,String username,String datetime) {
        int iRow=0;
        try{
            getConnection();
            String sql ="INSERT INTO `bishe`.`order`(`GOODSCOUNT`, `GOODSPRICE`, `CREATE_TIME`, `USERNAME`, `STATUS`, `ISEXIST`)\n" +
                    "VALUES (?, ?, ?, ?, '0', '1')";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1,goodsCount);
            pStmt.setString(2,goodsPrice);
            pStmt.setString(3,datetime);
            pStmt.setString(4,username);
        }catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeAll();
        }
        return iRow;

    }


/*
    public static List<OrderEntity> getAllOrderList() {
    }*/


    public static int insAddress(String address, String username) {
        int iRow = 0;
        try {
            getConnection();   // 取得连接信息
            String sql = "INSERT INTO entityuser_address(ADDRESS,username,ISEXIST) VALUES (?,?,'1')";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, username);
            pStmt.setString(2, address);
            iRow = pStmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeAll();
        }
        return iRow;
    }
}
