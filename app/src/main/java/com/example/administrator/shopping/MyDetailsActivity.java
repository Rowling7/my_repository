package com.example.administrator.shopping;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.shopping.adapter.AddressAdapter;
import com.example.administrator.shopping.adapter.EntityUserAdapter;
import com.example.administrator.shopping.dao.EntityUserDao;
import com.example.administrator.shopping.dao.ShoppingCartDao;
import com.example.administrator.shopping.entity.AddressEntity;
import com.example.administrator.shopping.entity.EntityUserEntity;

import java.util.List;

public class MyDetailsActivity extends AppCompatActivity {


    private TextView tv_sex;
    private TextView tv_age;
    private TextView tv_phone;
    private TextView tv_area;
    private TextView tv_name;
    private Handler mainHandler;     // 主线程

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 0 ) {
                String age = (String) msg.obj;
                tv_age.setText(age);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_details);
        SettingActivity.activityList.add(this);

        initView();

        tv_age = findViewById(R.id.tv_age);
        /*tv_phone = findViewById(R.id.tv_phone);
        tv_area = findViewById(R.id.tv_area);
        tv_sex = findViewById(R.id.tv_sex);*/
        selectAge();



        tv_name = findViewById(R.id.tv_name);
        Intent intent = getIntent();
        final String userNameForInfo = intent.getStringExtra("passValueForUser");//MyActivity的传值
        tv_name.setText(userNameForInfo);

    }

    private void initView() {
        mainHandler = new Handler(getMainLooper());//获取主线程
    }


    // 查询age
    public void selectAge() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent intent = getIntent();
                final String userNameForDetails = intent.getStringExtra("passValueForUser");//MyActivity的传值
                Log.i("4", "地址数量：" + userNameForDetails);
                String age = EntityUserDao.getUserAge(userNameForDetails);
                Message msg = Message.obtain();
                msg.what = 0;   // 查询结果
                msg.obj = age;
                // 向主线程发送数据
                handler.sendMessage(msg);

              //  selectSex();
            }
        }).start();
    }
/*
    // 查询sex
    public void selectSex() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent intent = getIntent();
                final String userNameForDetails = intent.getStringExtra("passValueForUser");//MyActivity的传值
                String sex = EntityUserDao.getUserSex(userNameForDetails);
                Message msgSex = Message.obtain();
                msgSex.what = 0;   // 查询结果
                msgSex.obj = sex;
                // 向主线程发送数据
                handler.sendMessage(msgSex);

                selectPhone();
            }
        }).start();
    }

    // 查询phone
    public void selectPhone() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent intent = getIntent();
                final String userNameForDetails = intent.getStringExtra("passValueForUser");//MyActivity的传值
                String phone = EntityUserDao.getUserPhone(userNameForDetails);
                Message msgPhone = Message.obtain();
                msgPhone.what = 0;   // 查询结果
                msgPhone.obj = phone;
                // 向主线程发送数据
                handler.sendMessage(msgPhone);
                selectArea();
            }
        }).start();
    }

    // 查询area
    public void selectArea() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent intent = getIntent();
                final String userNameForDetails = intent.getStringExtra("passValueForUser");//MyActivity的传值
                String area = EntityUserDao.getUserArea(userNameForDetails);
                Message msgArea = Message.obtain();
                msgArea.what = 0;   // 查询结果
                msgArea.obj = area;
                // 向主线程发送数据
                handler.sendMessage(msgArea);
            }
        }).start();
    }*/

}