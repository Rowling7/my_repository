package com.example.administrator.shopping.dao;

import android.util.Log;

import com.example.administrator.shopping.entity.AddressEntity;
import com.example.administrator.shopping.entity.EntityUserEntity;
import com.example.administrator.shopping.database.DbOpenHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EntityUserDao extends DbOpenHelper {

    /*login——登录  可用*/
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

    /*insert———注册  可用*/
    public static int insertUser(EntityUserEntity entityUserEntity) {
        int iRow = 0;
        try {
            getConnection();   // 取得连接信息
            String sql = "INSERT INTO entityuser(PASSWORD, USERNAME, SEX, PHONE, AGE, ADDRESS,UUID) VALUES (?,?,?,?,?,?,?)";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, entityUserEntity.getPassword());
            pStmt.setString(2, entityUserEntity.getUserName());
            pStmt.setString(3, entityUserEntity.getSex());
            pStmt.setString(4, entityUserEntity.getPhone());
            pStmt.setString(5, entityUserEntity.getAge());
            pStmt.setString(6, entityUserEntity.getAddress());
            pStmt.setString(7, entityUserEntity.getPhone());
            iRow = pStmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeAll();
        }
        return iRow;
    }

    /*update*/
    public static int updateAddress(EntityUserEntity entityUserEntity) {
        int iRow = 0;
        try {
            getConnection();   // 取得连接信息
            String sql = "update entityuser set address=? where username=?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, entityUserEntity.getAddress());
            pStmt.setString(2, entityUserEntity.getUserName());
            iRow = pStmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeAll();
        }
        return iRow;
    }

    /*查询用户信息  无用*/
    public List<EntityUserEntity> getUserInfoListByid(String userNameForInfo) {
        List<EntityUserEntity> list = new ArrayList<>();
        try {
            getConnection();
            String sql = "SELECT * FROM entityuser WHERE USERNAME=?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, userNameForInfo);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                EntityUserEntity item = new EntityUserEntity();
                item.setUuid(rs.getLong("uuid"));
                item.setUserName(rs.getString("username"));
                item.setAddress(rs.getString("address"));
                item.setArea(rs.getString("area"));
                item.setAge(rs.getString("age"));
                list.add(item);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeAll();
        }
        return list;
    }

    /*select  无用*/
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

    /*DELETE 无用*/
    public static int delAddress(EntityUserEntity entityUserEntity) {
        int iRow = 0;
        try {
            getConnection();   // 取得连接信息
            String sql = "UPDATE `bishe`.`entityuser` SET  `ADDRESS` = null  WHERE USERNAME = ?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, entityUserEntity.getUserName());
            iRow = pStmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeAll();
        }
        return iRow;
    }

    /*delete---->更改isexist为0   无用*/
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


