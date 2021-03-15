package com.example.administrator.shopping.dao;

import com.example.administrator.shopping.database.DbOpenHelper;
import com.example.administrator.shopping.entity.AddressEntity;
import com.example.administrator.shopping.entity.EntityUserEntity;

import java.util.ArrayList;
import java.util.List;

public class AddressDao extends DbOpenHelper {

    /*查询用户地址*/
    public List<AddressEntity> getAddressListByid(String username3) {
        List<AddressEntity> list = new ArrayList<>();
        try {
            getConnection();
            String sql = "SELECT * FROM entityuser_address\n" +
                    "WHERE USERNAME=? AND ISEXIST = 1";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, username3);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                AddressEntity item = new AddressEntity();
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


    /*insert———添加地址*/
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
