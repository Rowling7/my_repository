package com.example.administrator.shopping.dao;

import android.util.Log;

import com.example.administrator.shopping.database.DbOpenHelper;
import com.example.administrator.shopping.entity.AddressEntity;
import com.example.administrator.shopping.entity.OrderEntity;

import java.util.ArrayList;
import java.util.List;

public class OrderDao extends DbOpenHelper {


    public static int insOrder(String goodsCount, String goodsPrice, String username, String datetime) {
        int iRow = 0;
        try {
            getConnection();
            String sql = "INSERT INTO `bishe`.`order`(`GOODSCOUNT`, `GOODSPRICE`, `CREATE_TIME`, `USERNAME`, `STATUS`, `ISEXIST`,`isshouhuo`,`NUMBER`)\n" +
                    "VALUES (?,?,?, ?, '0', '1','1')";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, goodsCount);
            pStmt.setString(2, goodsPrice);
            pStmt.setString(3, datetime);
            pStmt.setString(4, username);
            iRow = pStmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeAll();
        }
        Log.i("iRow", "updateStatus: " + iRow);
        return iRow;

    }

    //可修改代码逻辑改变支付结果
    /*public static OrderEntity insOrder(String goodsCount, String goodsPrice, String username, String datetime) {
        OrderEntity orderEntity = null;
        try{
            getConnection();
            String sql ="INSERT INTO `bishe`.`order`(`GOODSCOUNT`, `GOODSPRICE`, `CREATE_TIME`, `USERNAME`, `STATUS`, `ISEXIST`)\n" +
                    "VALUES (?, ?, ?, ?, '0', '1')";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1,goodsCount);
            pStmt.setString(2,goodsPrice);
            pStmt.setString(3,datetime);
            pStmt.setString(4,username);
            rs = pStmt.executeUpdate();
            if (rs.next()) {
                orderEntity = new OrderEntity();
                orderEntity.setUuid(rs.getLong("uuid"));
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeAll();
        }
        Log.i("iRow" , "updateStatus: "+orderEntity);
        return orderEntity;

    }*/


    /*update——更改地址*/
    public static int updateStatus(String username) {
        int iRow = 0;
        try {
            getConnection();
            String sql = "UPDATE `bishe`.`order` SET  `STATUS` = '1'WHERE `username` = ?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, username);
            iRow = pStmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeAll();
        }
        Log.i("iRow2", "updateStatus: " + iRow);
        return iRow;
    }


    // 查询所有订单信息
    public List<OrderEntity> getOrderList(String username) {
        List<OrderEntity> list = new ArrayList<>();
        try {
            getConnection();
            String sql = "select * from bishe.order where username = ? and status = 1 and isshouhuo = 1";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, username);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                OrderEntity item = new OrderEntity();
                item.setUuid(rs.getLong("uuid"));
                item.setGoodsprice(rs.getString("goodsPrice"));
                //item.setPrice(rs.getString("price"));
                //item.setPicture(rs.getString("picture"));
                list.add(item);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeAll();
        }
        return list;
    }

    // 查询所有订单信息
    public List<OrderEntity> getNotPayOrderList(String username) {
        List<OrderEntity> list = new ArrayList<>();
        try {
            getConnection();
            String sql = "select * from bishe.order where username = ? and status = 0 and isexist = 1";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, username);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                OrderEntity item = new OrderEntity();
                item.setUuid(rs.getLong("uuid"));
                item.setGoodsprice(rs.getString("goodsPrice"));
                //item.setPrice(rs.getString("price"));
                //item.setPicture(rs.getString("picture"));
                list.add(item);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeAll();
        }
        return list;
    }


    public static int delOrder(long uuid) {
        int iRow = 0;
        try {
            getConnection();   // 取得连接信息
            String sql = "UPDATE `bishe`.`order` SET `ISEXIST` = '0' WHERE `UUID` = ?";//真
            //String sql = "update shoppingcart set isexist=0 where uuid=?";//伪
            pStmt = conn.prepareStatement(sql);
            pStmt.setLong(1, uuid);
            iRow = pStmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeAll();
        }
        return iRow;
    }

    public static int payOrder(long uuid) {
        int iRow = 0;
        try {
            getConnection();   // 取得连接信息
            String sql = "UPDATE `bishe`.`order` SET `status` = '1' WHERE `UUID` = ?";//真
            //String sql = "update shoppingcart set isexist=0 where uuid=?";//伪
            pStmt = conn.prepareStatement(sql);
            pStmt.setLong(1, uuid);
            iRow = pStmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeAll();
        }
        return iRow;
    }


    public static int ConOrder(long uuid) {
        int iRow = 0;
        try {
            getConnection();   // 取得连接信息
            String sql = "UPDATE `bishe`.`order` SET `isshouhuo` = '0' WHERE `UUID` = ?";//真
            //String sql = "update shoppingcart set isexist=0 where uuid=?";//伪
            pStmt = conn.prepareStatement(sql);
            pStmt.setLong(1, uuid);
            iRow = pStmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeAll();
        }
        return iRow;
    }


    // 查询所有订单信息
    public List<OrderEntity> getFinishList(String username) {
        List<OrderEntity> list = new ArrayList<>();
        try {
            getConnection();
            String sql = "select * from bishe.order where username = ? and status = 1 AND ISEXIST =1 and isshouhuo = 0";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, username);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                OrderEntity item = new OrderEntity();
                item.setUuid(rs.getLong("uuid"));
                item.setGoodsprice(rs.getString("goodsPrice"));
                item.setDescription(rs.getString("description"));
                //item.setPrice(rs.getString("price"));
                //item.setPicture(rs.getString("picture"));
                list.add(item);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeAll();
        }
        return list;
    }


    /*update——更改地址*/
    public static int setDescription(OrderEntity descriptionEdit) {
        int iRow = 0;
        try {
            getConnection();   // 取得连接信息
            String sql = "UPDATE `bishe`.`order` SET `description` = ? WHERE `UUID` = ? ";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, descriptionEdit.getDescription());
            pStmt.setLong(2, descriptionEdit.getUuid());
            iRow = pStmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeAll();
        }
        return iRow;
    }


    // 查询所有订单信息
    public List<OrderEntity> getHisWallet(String username) {
        List<OrderEntity> list = new ArrayList<>();
        try {
            getConnection();
            Log.i("TAG", "getHisWallet: " + username);
            String sql = "SELECT * FROM BISHE.order WHERE USERNAME =?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, username);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                OrderEntity item = new OrderEntity();
                // item.setUuid(rs.getLong("uuid"));
                item.setGoodsprice(rs.getString("GOODSPRICE"));
                item.setDatetime(rs.getString("create_time"));
                list.add(item);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeAll();
        }
        Log.i("TAG", "getHisWallet: " + list);
        return list;
    }

}
