package com.example.administrator.shopping;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/*import com.example.administrator.shopping.utils.CommonUtils;*/

import com.example.administrator.shopping.utils.ToastUtils;

import java.util.LinkedList;
import java.util.List;

public class SettingActivity extends AppCompatActivity {


    private Button btnExit;
    private Button btnRegister2;
    private Button btnRelogin;
    private Button btnBussiness2;
    private Button btnAdmin2;


    public static List<Activity> activityList = new LinkedList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        // SettingActivity.activityList.add(this);
        btnExit = findViewById(R.id.btn_exit);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                exit();
            }
        });
        btnRelogin = findViewById(R.id.btn_reLogin);
        btnRelogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(SettingActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnRegister2 = findViewById(R.id.btn_register2);
        btnRegister2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(SettingActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnAdmin2 = findViewById(R.id.btn_admin2);
        btnAdmin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(SettingActivity.this, ShopActivity.class);
                startActivity(intent);
            }
        });

        btnBussiness2 = findViewById(R.id.btn_bussiness2);
        btnBussiness2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(SettingActivity.this, BusinessActivity.class);
                startActivity(intent);
            }
        });
    }

    public void exit() {
        for (Activity act : activityList) {
            act.finish();
        }
        ToastUtils.showMsg(SettingActivity.this, "已退出");
        System.exit(0);

    }


}