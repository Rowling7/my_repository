package com.example.administrator.shopping;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/*import com.example.administrator.shopping.utils.CommonUtils;*/

import com.example.administrator.shopping.dao.EntityUserDao;
import com.example.administrator.shopping.entity.EntityUserEntity;
import com.example.administrator.shopping.utils.ToastUtils;

import java.util.LinkedList;
import java.util.List;

public class SettingActivity extends AppCompatActivity {


    private Button btnExit;
    private Button btnRegister2;
    private Button btnRelogin;
    private Button btn_delUaser;
    private ImageView go_back;

    private EntityUserDao entityUserDao;
    public static List<Activity> activityList = new LinkedList();
    private Handler mainHandler;
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {

        }
    };// 主线程

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        SettingActivity.activityList.add(this);
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

        /*btnRegister2 = findViewById(R.id.btn_register2);
        btnRegister2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(SettingActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });*/


        btn_delUaser = findViewById(R.id.btn_delUSer);
        btn_delUaser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                final String userNameForSet = intent.getStringExtra("passValueForSet");//登陆后的传值
                new AlertDialog.Builder(SettingActivity.this)
                        .setTitle("注销！")
                        .setMessage("您确定要注销吗：" + userNameForSet + "？")
                        .setMessage("此操作不可逆！")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                doDelUser();
                            }
                        })
                        .setNegativeButton("取消", null)
                        .create().show();
            }
        });

        go_back = findViewById(R.id.go_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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

    //删除操作
    private void doDelUser() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent intent = getIntent();
                final String userNameForSet = intent.getStringExtra("passValueForSet");//登陆后的传值
                final int iRow = EntityUserDao.delUser(userNameForSet);

                intent = new Intent(SettingActivity.this, MyActivity.class);
                intent.putExtra("passValue", userNameForSet);
                startActivity(intent);
                ToastUtils.showMsg(SettingActivity.this, "已注销用户!");
                // loadAddress();  // 重新加载数据

            }
        }).start();
    }

}