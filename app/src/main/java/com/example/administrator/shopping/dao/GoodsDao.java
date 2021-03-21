package com.example.administrator.shopping.dao;

import android.util.Log;
import android.widget.EditText;

import com.example.administrator.shopping.entity.GoodsEntity;
import com.example.administrator.shopping.database.DbOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class GoodsDao extends DbOpenHelper {


    // 查询所有商品信息
    public List<GoodsEntity> getAllGoodsList() {
        List<GoodsEntity> list = new ArrayList<>();
        try {
            getConnection();
            String sql = "select * from goods";
            pStmt = conn.prepareStatement(sql);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                GoodsEntity item = new GoodsEntity();
                item.setUuid(rs.getString("uuid"));
                item.setName(rs.getString("name"));
                item.setPrice(rs.getString("price"));
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

    // 查询商品信息
    public List<GoodsEntity> getSearchGoods(String etinsNameFofSearch) {
        List<GoodsEntity> list = new ArrayList<>();
        try {
            getConnection();
            String sql = "select * from goods where name =?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, etinsNameFofSearch);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                GoodsEntity item = new GoodsEntity();
                item.setUuid(rs.getString("uuid"));
                item.setName(rs.getString("name"));
                item.setPrice(rs.getString("price"));
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


    /*添加购物车中的数据*/
    public static int insCart(String uuid, String username) {
        int iRow = 0;
        try {
            getConnection();   // 取得连接信息
            String sql = "INSERT INTO shoppingcart(  GOODS_UUID, ISEXIST, USERNAME) VALUES (?, '1', ?);\n";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, uuid);
            pStmt.setString(2, username);
            iRow = pStmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeAll();
        }
        return iRow;
    }


}
