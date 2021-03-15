package com.example.administrator.shopping.dao;

import com.example.administrator.shopping.database.DbOpenHelper;
import com.example.administrator.shopping.entity.ShoppingCartEntity;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartDao extends DbOpenHelper {

    // 查询所有商品信息
    public List<ShoppingCartEntity> getCartListById() {
        List<ShoppingCartEntity> list = new ArrayList<>();
        try {
            getConnection();
            String sql = "select * from goods";
            pStmt = conn.prepareStatement(sql);
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
