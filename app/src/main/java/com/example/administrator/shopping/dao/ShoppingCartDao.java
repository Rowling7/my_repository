package com.example.administrator.shopping.dao;

import android.util.Log;

import com.example.administrator.shopping.database.DbOpenHelper;
import com.example.administrator.shopping.entity.EntityUserEntity;
import com.example.administrator.shopping.entity.ShoppingCartEntity;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartDao extends DbOpenHelper {


    // 查询所有商品信息,如果isexist为1 则不显示
    public List<ShoppingCartEntity> getCartListById(String userNameForCart) {
        List<ShoppingCartEntity> list = new ArrayList<>();
        try {
            getConnection();
          /*  String sql = "SELECT sc.uuid ,g.NAME as goodsName, g.price, g.number,g.originplace,g.description\n" +
                    "FROM shoppingcart sc\n" +
                    "LEFT JOIN goods g ON sc.GOODS_UUID = g.UUID \n" +
                    "WHERE sc.USERNAME = ? AND SC.ISEXIST = 1";*/
            String sql = "SELECT sc.uuid ,g.NAME as goodsName, g.price,g.picture, g.amount,g.originplace,g.description,g.uuid as goodsUuid\n" +
                    "FROM shoppingcart sc\n" +
                    "LEFT JOIN goods g ON sc.GOODS_UUID = g.UUID \n" +
                    "WHERE sc.USERNAME = ? AND SC.ISEXIST = 1 AND amount >0";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, userNameForCart);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                ShoppingCartEntity item = new ShoppingCartEntity();
                item.setUuid(rs.getLong("sc.uuid"));
                item.setName(rs.getString("goodsName"));
                item.setPrice(rs.getString("price"));
                item.setNumber(rs.getString("amount"));
                item.setDescription(rs.getString("description"));
                item.setOriginPlace(rs.getString("originPlace"));
                item.setPicture(rs.getString("picture"));
                item.setGoods_uuid(rs.getString("goodsUuid"));
                list.add(item);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeAll();
        }
        Log.i("UUID", "!!: "+list);
        return list;
    }

//+++
    public static int delCart(long uuid) {
        int iRow = 0;
        try {
            getConnection();   // 取得连接信息
            /*String sql = "UPDATE `bishe`.`goods`\n" +
                    "LEFT JOIN shoppingcart sc ON sc.GOODS_UUID = goods.UUID\n" +
                    "SET `number` = number+1 \n" +
                    "WHERE sc.UUID = ?";//真*/
            String sql = "UPDATE `bishe`.`goods`\n" +
                    "LEFT JOIN shoppingcart sc ON sc.GOODS_UUID = goods.UUID\n" +
                    "SET `amount` = amount+1 \n" +
                    "WHERE sc.UUID = ?";//真
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

//---
    public static int doLessCart(long uuid) {
        int iRow = 0;
        try {
            getConnection();   // 取得连接信息
            /*String sql = "UPDATE `bishe`.`goods`\n" +
                    "LEFT JOIN shoppingcart sc ON sc.GOODS_UUID = goods.UUID\n" +
                    "SET `number` = number-1 \n" +
                    "WHERE sc.UUID = ?";//真*/
            String sql = "UPDATE `bishe`.`goods`\n" +
                    "LEFT JOIN shoppingcart sc ON sc.GOODS_UUID = goods.UUID\n" +
                    "SET `amount` = amount-1 \n" +
                    "WHERE sc.UUID = ?";//真
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


    /*查询购物车总金额*/
    public static String getCartSum(String userNameForCart) {
        String sum = null;
        try {
            getConnection();
           /* String sql = "SELECT cast(SUm(g.price*g.number)as  decimal(15,2)) as SUM\n" +
                    "from goods g\n" +
                    "LEFT JOIN shoppingcart sc on sc.GOODS_UUID = g.uuid\n" +
                    "where sc.username =? and sc.isexist = 1";*/
            String sql = "SELECT cast(SUm(g.price*g.amount)as  decimal(15,2)) as SUM\n" +
                    "from goods g\n" +
                    "LEFT JOIN shoppingcart sc on sc.GOODS_UUID = g.uuid\n" +
                    "where sc.username =? and sc.isexist = 1";
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

    /*购物车总个数*/
    public static String getCartCount(String userNameForCart) {
        String cartConut = null;
        try {
            getConnection();
            String sql = "SELECT count(price) as cartCount\n" +
                    "from goods g\n" +
                    "LEFT JOIN shoppingcart sc on sc.GOODS_UUID = g.uuid\n" +
                    "where username =? and sc.isexist = 1";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, userNameForCart);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                cartConut = rs.getString("cartCount");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return cartConut;
    }

    /*清空购物车中的胡数据*/
    public static int delAllCart(String userNameForCart) {
        int iRow = 0;
        try {
            getConnection();   // 取得连接信息
            String sql = "DELETE from  shoppingcart  WHERE username  = ?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, userNameForCart);
            iRow = pStmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeAll();
        }
        return iRow;
    }

    /*清空为 0 的数据*/
    public static int delGoods(long uuid) {
        int iRow = 0;
        try {
            getConnection();   // 取得连接信息
            String sql = "DELETE from  shoppingcart  WHERE UUID = ?";
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

}
