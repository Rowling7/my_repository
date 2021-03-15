package com.example.administrator.shopping.dao;

import android.util.Log;

import com.example.administrator.shopping.database.DbOpenHelper;
import com.example.administrator.shopping.entity.ShoppingCartEntity;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartDao extends DbOpenHelper {

    // 查询所有商品信息
    public List<ShoppingCartEntity> getCartListById(String userNameForCart) {
        List<ShoppingCartEntity> list = new ArrayList<>();
        try {
            getConnection();
            String sql = "SELECT sc.uuid ,g.NAME, g.price \n" +
                    "FROM shoppingcart sc\n" +
                    "LEFT JOIN goods g ON sc.GOODS_UUID = g.UUID \n" +
                    "WHERE USERNAME = ?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, userNameForCart);
            Log.i("0", "userNameForCart：" + userNameForCart);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                ShoppingCartEntity item = new ShoppingCartEntity();
                item.setUuid(rs.getLong("uuid"));
                item.setName(rs.getString("name"));
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



}
