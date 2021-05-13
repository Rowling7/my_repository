package com.example.administrator.shopping.dao;

import android.util.Log;

import com.example.administrator.shopping.entity.EntityUserEntity;
import com.example.administrator.shopping.database.DbOpenHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EntityUserDao extends DbOpenHelper {

    public static final String TAG = "OUTPUT";

    /*login——登录  可用*/
    public static EntityUserEntity login(String UserName, String Password) {//Activity 传值过来
        EntityUserEntity entityUserEntity = null;
        try {
            getConnection();   // 取得连接信息
            String sql = "select * from entityuser where  username=? and password= ? AND ISEXIST = 1 ";//用用户名和密码同时作为条件如果能搜索到信息就是成功的，搜索结果为空不成功
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
            String sql = "INSERT INTO entityuser(PASSWORD, USERNAME, SEX, PHONE, AGE, realname,area,create_time,isexist) VALUES (?,?,?,?,?,?,?,?,1)";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, entityUserEntity.getPassword());
            pStmt.setString(2, entityUserEntity.getUserName());
            pStmt.setString(3, entityUserEntity.getSex());
            pStmt.setString(4, entityUserEntity.getPhone());
            pStmt.setString(5, entityUserEntity.getAge());
            pStmt.setString(6, entityUserEntity.getRealName());
            pStmt.setString(7, entityUserEntity.getArea());
            pStmt.setString(8, entityUserEntity.getCreate_time());
            iRow = pStmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeAll();
        }
        return iRow;
    }


    /**
     * 查询
     *
     * @param userNameForCart
     * @return
     */
    /*查询钱包金额*/
    public static String getUserWallet(String userNameForCart) {
        String walletgeneral = null;   // 购物车总价格

        try {
            getConnection();
            String sql = "SELECT cast(wallet as  decimal(15,2))as wallet from entityuser where username =?AND ISEXIST =1";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, userNameForCart);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                walletgeneral = rs.getString("wallet");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return walletgeneral;
    }

    /*查询钱包金额*/
    public static String getUserWallet2(String userNameForCart) {
        String walletgeneral = null;   // 购物车总价格

        try {
            getConnection();
            String sql = "SELECT cast(wallet as  decimal(15,2))as wallet from entityuser where username =?AND ISEXIST =1";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, userNameForCart);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                walletgeneral = rs.getString("wallet");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Log.i(TAG, "getUserWallet: " + walletgeneral);
        return walletgeneral;
    }

    /*update*/
    public static int updateAddress(EntityUserEntity entityUserEntity) {
        int iRow = 0;
        try {
            getConnection();   // 取得连接信息
            String sql = "update entityuser set address=? where username=?AND ISEXIST =1";
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

    /*查询age*/
    public static String getUserAge(String userNameForDetails) {
        String age = null;   // 购物车总价格

        try {
            getConnection();

            String sql = "SELECT cast( DATEDIFF(CURDATE(),entityuser.birthday)/360 as decimal(15,0)) as age from entityuser WHERE username =? AND ISEXIST =1";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, userNameForDetails);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                age = rs.getString("age");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Log.i(TAG, "年龄：" + age);
        return age;
    }

    /*sex*/
    public static String getUserSex(String userNameForDetails) {
        String sex = null;   // 购物车总价格

        try {
            getConnection();
            String sql = "SELECT sex from entityuser WHERE username =?AND ISEXIST =1";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, userNameForDetails);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                sex = rs.getString("sex");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Log.i(TAG, "性别：" + sex);
        return sex;
    }

    /*phone*/
    public static String getUserPhone(String userNameForDetails) {
        String phone = null;   // 购物车总价格

        try {
            getConnection();
            String sql = "SELECT phone from entityuser WHERE username = ? AND ISEXIST =1";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, userNameForDetails);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                phone = rs.getString("phone");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Log.i(TAG, "手机号：" + phone);
        return phone;
    }

    /*area*/
    public static String getUserArea(String userNameForDetails) {
        String area = null;
        try {
            getConnection();
            String sql = "SELECT area from entityuser WHERE username = ? AND ISEXIST =1";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, userNameForDetails);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                area = rs.getString("area");

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Log.i(TAG, "地区：" + area);
        return area;
    }

    /*realname*/
    public static String getUserRealName(String userName2) {
        String realName = null;   // 购物车总价格

        try {
            getConnection();
            String sql = "SELECT realname from entityuser WHERE username =?AND ISEXIST =1";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, userName2);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                realName = rs.getString("realname");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Log.i(TAG, "真实姓名：" + realName);
        return realName;
    }

    /**
     * 删除
     *
     * @param userNameForSet
     * @return
     */
    /*delete——删除用户*/
    public static int delUser(String userNameForSet) {
        int iRow = 0;
        try {
            getConnection();   // 取得连接信息
            String sql = "update entityuser set isexist=0 where username=?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, userNameForSet);
            iRow = pStmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeAll();
        }
        return iRow;
    }


    /**
     * 修改
     *
     * @param keyForEdit
     * @param newAge
     * @return
     */
    /*update——年龄*/
    public static int updateAge(String keyForEdit, String newAge) {
        int iRow = 0;
        try {
            getConnection();   // 取得连接信息
            String sql = "update entityuser set age=? where username=?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, newAge);
            pStmt.setString(2, keyForEdit);
            iRow = pStmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeAll();
        }
        return iRow;
    }

    /*update——手机*/
    public static int updatePhone(String keyForEdit, String newPhone) {
        int iRow = 0;
        try {
            getConnection();   // 取得连接信息
            String sql = "update entityuser set phone=? where username=?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, newPhone);
            pStmt.setString(2, keyForEdit);
            iRow = pStmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeAll();
        }
        return iRow;
    }

    /*update——性别*/
    public static int updateSex(String keyForEdit, String newSex) {
        int iRow = 0;
        try {
            getConnection();   // 取得连接信息
            String sql = "update entityuser set sex=? where username=?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, newSex);
            pStmt.setString(2, keyForEdit);
            iRow = pStmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeAll();
        }
        return iRow;
    }

    /*update——地区*/
    public static int updateArea(String keyForEdit, String newArea) {
        int iRow = 0;
        try {
            getConnection();   // 取得连接信息
            String sql = "update entityuser set area=? where username=?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, newArea);
            pStmt.setString(2, keyForEdit);
            iRow = pStmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeAll();
        }
        return iRow;
    }

    public static int addWallet(String userName, String jine) {
        int iRow = 0;
        try {
            getConnection();   // 取得连接信息
            String sql = "UPDATE `bishe`.`entityuser` SET  wallet = wallet + ? WHERE username = ?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(2, userName);
            pStmt.setString(1, jine);
            iRow = pStmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeAll();
        }
        return iRow;
    }


    //查询未付款数量
    public static String getNumbForNopay(String userName) {
        String numbForNopay = null;   // 购物车总价格
        try {
            getConnection();
            String sql = "SELECT COUNT(STATUS) as numbForNopay FROM BISHE.ORDER  WHERE STATUS = 0 AND ISEXIST =1 AND USERNAME = ?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, userName);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                numbForNopay = rs.getString("numbForNopay");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Log.i(TAG, "未付款: " + numbForNopay);
        return numbForNopay;
    }


    //查询待收货数量
    public static String getNumbForReceived(String userName) {
        String numbForNoReceived = null;
        try {
            getConnection();
            String sql = "SELECT COUNT(STATUS) as numbForReceived FROM BISHE.ORDER  WHERE STATUS = 1 AND ISSHOUHUO = 1 AND ISEXIST =1 AND USERNAME = ?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, userName);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                numbForNoReceived = rs.getString("numbForReceived");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Log.i(TAG, "未收货: " + numbForNoReceived);
        return numbForNoReceived;
    }

    //查询评价数量
    public static String getNumbForStar(String userName) {
        String numbForStar = null;
        try {
            getConnection();
            String sql = "SELECT COUNT(UUID) as numbForStar FROM BISHE.ORDER  WHERE STATUS = 1 AND ISSHOUHUO = 0 AND ISEXIST =1 AND USERNAME = ? and DESCRIPTION IS NOT NULL";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, userName);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                numbForStar = rs.getString("numbForStar");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Log.i(TAG, "评价: " + numbForStar);
        return numbForStar;
    }


    //判断是否能付款
    public static String confWallet(String goodsPrice, String username) {
        String isNull = null;
        try {
            getConnection();
            String sql = "SELECT (EU.WALLET-?)>0 AS ISNULL FROM entityuser eu WHERE USERNAME =?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, goodsPrice);
            pStmt.setString(2, username);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                isNull = rs.getString("isnull");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Log.i(TAG, "是否可支付: " + isNull);
        return isNull;
    }


    //恢复钱包
    public static int reConfWallet(String goodsPrice, String username) {
        int iRow = 0;
        try {
            getConnection();
            String sql = "UPDATE `bishe`.`entityuser` SET `wallet` = wallet + ? WHERE `username` = ?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, goodsPrice);
            pStmt.setString(2, username);
            iRow = pStmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        closeAll();
        return iRow;
    }

    /*update——更改地址*/
    public static int updateWallet(String goodsPrice, String username) {
        int iRow = 0;
        try {
            getConnection();
            String sql = "UPDATE `bishe`.`entityuser` EU  SET `wallet` = EU.wallet- ? WHERE USERNAME = ? ";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, goodsPrice);
            pStmt.setString(2, username);
            iRow = pStmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeAll();
        }
        Log.i("iRow2", "updateStatus: " + iRow);
        return iRow;
    }

    /*insert———添加头像*/
    public static int insertHead(String imageString, String username) {
        int iRow = 0;
        try {
            getConnection();   // 取得连接信息
            String sql = "UPDATE `bishe`.`entityuser` SET  `head` = ? WHERE username = ?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, imageString);
            pStmt.setString(2, username);
            iRow = pStmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeAll();
        }
        return iRow;
    }


    //查询头像
    public static String getHead(String userName) {
        String imgHead = null;
        try {
            getConnection();
            String sql = "SELECT head FROM `entityuser` WHERE username =?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, userName);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                imgHead = rs.getString("head");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Log.i(TAG, "未收货: " + imgHead);
        return imgHead;
    }

    /**
     * 无用方法
     *
     * @param userNameForInfo
     * @return
     */
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
                pStmt = conn.prepareStatement(sql);
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


