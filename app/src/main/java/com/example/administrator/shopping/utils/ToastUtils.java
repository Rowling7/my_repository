package com.example.administrator.shopping.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;


//土司消息提示
public class ToastUtils {
    public static Toast myToast;


    //默认显示
    public static void showMsg(Context context, String msg) {
        if (myToast == null) {
            myToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            myToast.setText(msg);
        }
        myToast.show();
    }


    //显示在中间
    public static void showCenterMsg(Context context, String msg) {
        if (myToast == null) {
            myToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
            myToast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            myToast.setText(msg);
        }
        myToast.show();
    }

    //显示在顶部
    public static void showTopMsg(Context context, String msg) {
        if (myToast == null) {
            myToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
            myToast.setGravity(Gravity.TOP, 0, 0);
        } else {
            myToast.setText(msg);
        }
        myToast.show();
    }

}
