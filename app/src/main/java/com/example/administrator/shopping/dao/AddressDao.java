package com.example.administrator.shopping.dao;

import com.example.administrator.shopping.database.DbOpenHelper;
import com.example.administrator.shopping.entity.EntityUserEntity;

import java.util.ArrayList;
import java.util.List;

public class AddressDao extends DbOpenHelper {


    // 查询所有地址信息
    public List<EntityUserEntity> getAllAddressList() {
        List<EntityUserEntity> list = new ArrayList<>();
        try {
            getConnection();
            String sql = "select * from entityuser";
            pStmt = conn.prepareStatement(sql);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                EntityUserEntity item = new EntityUserEntity();
                item.setUuid(rs.getLong("uuid"));
                item.setAddress(rs.getString("address"));

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
