package com.example.administrator.shopping.dao;

import android.util.Log;

import com.example.administrator.shopping.database.DbOpenHelper;
import com.example.administrator.shopping.entity.ShoppingCartEntity;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartDao extends DbOpenHelper {

    // 查询所有商品信息,如果isexist为1 则不显示
    public List<ShoppingCartEntity> getCartListById(String userNameForCart) {
        List<ShoppingCartEntity> list = new ArrayList<>();
        try {
            getConnection();
            String sql = "SELECT sc.uuid ,g.NAME as goodsName, g.price \n" +
                    "FROM shoppingcart sc\n" +
                    "LEFT JOIN goods g ON sc.GOODS_UUID = g.UUID \n" +
                    "WHERE sc.USERNAME = ? AND SC.ISEXIST = 1";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, userNameForCart);
            Log.i("0", "userNameForCart：" + userNameForCart);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                ShoppingCartEntity item = new ShoppingCartEntity();
                item.setUuid(rs.getLong("sc.uuid"));
                item.setName(rs.getString("goodsName"));
                item.setPrice(rs.getString("price"));
                list.add(item);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeAll();
        }
        return list;
    }

    /*删除购物车中的胡数据*/
    public static int delCart(long uuid) {
        int iRow = 0;
        try {
            getConnection();   // 取得连接信息
            String sql = "update shoppingcart set isexist=0 where uuid=?";
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


    /*查询购物车总金额*/
    public static String getCartSum(String userNameForCart) {
        String sum = null;   // 购物车总价格

        try {
            getConnection();
            String sql = "SELECT cast(SUm(price)as  decimal(15,2)) as SUM\n" +
                    "from goods g\n" +
                    "LEFT JOIN shoppingcart sc on sc.GOODS_UUID = g.uuid\n" +
                    "where username =? and sc.isexist = 1";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, userNameForCart);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                sum = rs.getString("SUM");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return sum;
    }
}
