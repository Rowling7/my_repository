package com.example.administrator.shopping.Dao;

import com.example.administrator.shopping.Entity.EntityUserEntity;
import com.example.administrator.shopping.database.DbOpenHelper;
import com.example.administrator.shopping.utils.DBUtils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EntityUserDao extends DbOpenHelper {

    /*login*/
    public static EntityUserEntity login(String UserName, String Password) {//Activity 传值过来
        EntityUserEntity entityUserEntity = null;
        try {
            getConnection();   // 取得连接信息
            String sql = "select * from entityuser where username=? and password=?";//用用户名和密码同时作为条件如果能搜索到信息就是成功的，搜索结果为空不成功
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, UserName);// 获取用户名
            pStmt.setString(2, Password);//获取密码
            rs = pStmt.executeQuery();//回传数据
            if (rs.next()) {
                entityUserEntity = new EntityUserEntity();
                entityUserEntity.setUuid(rs.getLong("uuid"));
                entityUserEntity.setUserName(UserName);
                entityUserEntity.setPassword(Password);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeAll();
        }
        return entityUserEntity;
    }

    /*select*/
    public List<EntityUserEntity> getUserName() {
        List<EntityUserEntity> list = new ArrayList<EntityUserEntity>();
        String sql = "select * from entityuser";
        getConnection();
        try {
            if (conn != null && (!conn.isClosed())) {
                pStmt = (PreparedStatement) conn.prepareStatement(sql);
                if (pStmt != null) {
                    rs = pStmt.executeQuery();
                    if (rs != null) {
                        while (rs.next()) {
                            EntityUserEntity u = new EntityUserEntity();
                            u.setUuid(rs.getLong("uuid"));
                            u.setPassword(rs.getString("password"));
                            u.setUserName(rs.getString("name"));
                            u.setUserType(rs.getString("usertype"));

                            list.add(u);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeAll();//关闭相关操作
        return list;
    }


    /*insert*/
    public static int insertUser(EntityUserEntity entityUserEntity) {
        int iRow = 0;
        try {
            getConnection();   // 取得连接信息
            String sql = "insert into entityuser(password,userName,sex,phone,age,address) values(?,?,?,?,?,?)";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, entityUserEntity.getPassword());
            pStmt.setString(2, entityUserEntity.getUserName());
            pStmt.setString(3, entityUserEntity.getSex());
            pStmt.setString(4, entityUserEntity.getPhone());
            pStmt.setString(5, entityUserEntity.getAge());
            pStmt.setString(6, entityUserEntity.getAddress());
           // pStmt.setString(7, entityUserEntity.getArea());
            iRow = pStmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeAll();
        }
        return iRow;
    }


    /*update*/
    public int updatePassword(EntityUserEntity entityUserEntity) {
        int iRow = 0;
        try {
            getConnection();   // 取得连接信息
            String sql = "update entityuser set password=? where userName=?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(2, entityUserEntity.getPassword());
            iRow = pStmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeAll();
        }
        return iRow;
    }


    /*delete---->更改isexist为0*/
    public int deletebyuuid(EntityUserEntity entityUserEntity) {
        int iRow = 0;
        try {
            getConnection();   // 取得连接信息
            String sql = "update entityuser set username   =?,password=? where uuid=?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, entityUserEntity.getUserName());
            pStmt.setString(2, entityUserEntity.getPassword());
            iRow = pStmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeAll();
        }
        return iRow;
    }
}


