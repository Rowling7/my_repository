package com.example.administrator.shopping.dao;


import android.util.Log;

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
            String sql = "select uuid,name,price,description,originPlace,picture,uuid from bishe.goods";
            pStmt = conn.prepareStatement(sql);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                GoodsEntity item = new GoodsEntity();
                item.setUuid(rs.getString("uuid"));
                item.setName(rs.getString("name"));
                item.setPrice(rs.getString("price"));
                item.setDescription(rs.getString("description"));
                item.setOriginPlace(rs.getString("originPlace"));
                item.setPicture(rs.getString("picture"));
            /*    byte[] imageBytes = Base64.decode(rs.getString("picture"), Base64.DEFAULT);
                Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                item.setPicture(decodedImage+"");*/

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
                item.setDescription(rs.getString("description"));
                item.setOriginPlace(rs.getString("originPlace"));
                item.setPicture(rs.getString("picture"));
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
            String sql = "INSERT INTO shoppingcart(GOODS_UUID, ISEXIST, USERNAME) VALUES (?, '1', ?);\n";
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


    //分类
    public List<GoodsEntity> getnongchanpinList() {
        List<GoodsEntity> list = new ArrayList<>();
        try {
            getConnection();
            String sql = "select * from goods where type = 0";
            pStmt = conn.prepareStatement(sql);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                GoodsEntity item = new GoodsEntity();
                item.setUuid(rs.getString("uuid"));
                item.setName(rs.getString("name"));
                item.setPrice(rs.getString("price"));
                item.setDescription(rs.getString("description"));
                item.setOriginPlace(rs.getString("originPlace"));
                item.setPicture(rs.getString("picture"));
                list.add(item);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeAll();
        }
        return list;
    }

    // 查询所有商品信息
    public List<GoodsEntity> getlingshiList() {
        List<GoodsEntity> list = new ArrayList<>();
        try {
            getConnection();
            String sql = "select * from goods where type = 4";
            pStmt = conn.prepareStatement(sql);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                GoodsEntity item = new GoodsEntity();
                item.setUuid(rs.getString("uuid"));
                item.setName(rs.getString("name"));
                item.setPrice(rs.getString("price"));
                item.setDescription(rs.getString("description"));
                item.setOriginPlace(rs.getString("originPlace"));
                item.setPicture(rs.getString("picture"));
                list.add(item);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeAll();
        }
        return list;
    }

    // 查询所有商品信息
    public List<GoodsEntity> getshuiguoList() {
        List<GoodsEntity> list = new ArrayList<>();
        try {
            getConnection();
            String sql = "select * from goods where type = 1";
            pStmt = conn.prepareStatement(sql);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                GoodsEntity item = new GoodsEntity();
                item.setUuid(rs.getString("uuid"));
                item.setName(rs.getString("name"));
                item.setPrice(rs.getString("price"));
                item.setDescription(rs.getString("description"));
                item.setOriginPlace(rs.getString("originPlace"));
                item.setPicture(rs.getString("picture"));
                list.add(item);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeAll();
        }
        return list;
    }

    // 查询所有商品信息
    public List<GoodsEntity> getyinpinList() {
        List<GoodsEntity> list = new ArrayList<>();
        try {
            getConnection();
            String sql = "select * from goods where type = 3";
            pStmt = conn.prepareStatement(sql);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                GoodsEntity item = new GoodsEntity();
                item.setUuid(rs.getString("uuid"));
                item.setName(rs.getString("name"));
                item.setPrice(rs.getString("price"));
                item.setDescription(rs.getString("description"));
                item.setOriginPlace(rs.getString("originPlace"));
                item.setPicture(rs.getString("picture"));
                list.add(item);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeAll();
        }
        return list;
    }
    //结束分类

    /*update*/
    public static int updateNumber() {
        int iRow = 0;
        try {
            getConnection();
            // String sql = "UPDATE `bishe`.`goods`   SET `number` = '1'";
            String sql = "UPDATE `bishe`.`goods`   SET `amount` = '1'";
            pStmt = conn.prepareStatement(sql);
            iRow = pStmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeAll();
        }
        return iRow;
    }

    /*查询goodsAmount*/
    public static int getGoodsAmount(long uuid) {
        int goodsAmount = 0;
        try {
            getConnection();
            String sql = "SELECT amount AS goodsAmount\n" +
                    "FROM goods g\n" +
                    "LEFT JOIN shoppingcart sc ON sc.GOODS_UUID = g.UUID\n" +
                    "WHERE SC.UUID =?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setLong(1, uuid);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                goodsAmount = (int) rs.getLong("goodsAmount");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Log.i("TAG", "??: " + uuid);
        return goodsAmount;
    }


    public static String getGoodsPicture(String uuid) {
        String imgGoods = null;
        try {
            getConnection();
            String sql = "SELECT picture FROM goods WHERE uuid = ?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, uuid);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                imgGoods =  rs.getString("picture");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return imgGoods;
    }
    public static String getGoodsName(String uuid) {
        String name = null;
        try {
            getConnection();
            String sql = "SELECT name FROM goods WHERE uuid = ?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, uuid);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                name =  rs.getString("name");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Log.i("TAG", "??: " + uuid);
        return name;
    }
    public static String getGoodsPrice(String uuid) {
        String price = null;
        try {
            getConnection();
            String sql = "SELECT price FROM goods WHERE uuid = ?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, uuid);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                price =  rs.getString("price");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return price;
    }
    public static String getGoodsPlace(String uuid) {
        String place = null;
        try {
            getConnection();
            String sql = "SELECT originplace FROM goods WHERE uuid = ?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, uuid);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                place =  rs.getString("originplace");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return place;
    }
    public static String getGoodsDescription(String uuid) {
        String description = null;
        try {
            getConnection();
            String sql = "SELECT description FROM goods WHERE uuid = ?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, uuid);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                description =  rs.getString("description");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return description;
    }
}
