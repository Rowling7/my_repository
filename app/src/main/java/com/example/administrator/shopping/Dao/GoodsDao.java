package com.example.administrator.shopping.Dao;

import com.example.administrator.shopping.Entity.GoodsEntity;
import com.example.administrator.shopping.database.DbOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class GoodsDao extends DbOpenHelper {


    // 查询所有商品信息
    public List<GoodsEntity> getAllGoodsList() {
        List<GoodsEntity> list = new ArrayList<>();
        try {
            getConnection();
            String sql = "select uuid,name,price  from goods";
            pStmt = conn.prepareStatement(sql);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                GoodsEntity item = new GoodsEntity();
                item.setUuid(rs.getString("uuid"));
                item.setName(rs.getString("name"));
                item.setPrice(rs.getString("price"));
                //item.setPicture(rs.getInt("picture"));
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
