package com.example.administrator.shopping.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DBUtils {
    public static final String DATABASE_NAME = "Shopping";
    public static final String DATABASE_TABLE = "shop";
    public static final int DATABASE_VERION = 1;
    public static final String SHOPPING_ID = "id";
    public static final String SHOPPING_TITLE = "title";
    public static final String SHOPPING_PRICE = "price";


    /**
     * 获取当前时间的字符串
     * @return "yyyy-MM-dd HH:mm:ss" 格式的时间字符串
     */
    public static String getDateStrFromNow(){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date());
    }
}
