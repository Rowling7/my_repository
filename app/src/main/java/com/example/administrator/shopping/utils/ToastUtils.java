package com.example.administrator.shopping.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {
    public static Toast myToast;

    public static void showMsg(Context context, String msg) {
        if (myToast == null) {
            myToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            myToast.setText(msg);
        }
        myToast.show();
    }

}
