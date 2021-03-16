package com.example.administrator.shopping;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.shopping.adapter.AddressAdapter;
import com.example.administrator.shopping.adapter.EntityUserAdapter;
import com.example.administrator.shopping.dao.EntityUserDao;
import com.example.administrator.shopping.entity.AddressEntity;
import com.example.administrator.shopping.entity.EntityUserEntity;

import java.util.List;

public class MyDetailsActivity extends AppCompatActivity {

    private TextView tv_userInfo;
    private ListView lv_userInfo;
    private EntityUserDao entityUserDao;
    private List<EntityUserEntity> userInfoList;
    private EntityUserAdapter entityUserAdapter;

    private Handler mainHandler;     // 主线程

    private Handler handler = new Handler() {

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_details);
        SettingActivity.activityList.add(this);

        Intent intent = getIntent();
        final String userNameForInfo = intent.getStringExtra("passValue2");//登陆后的传值

        tv_userInfo = findViewById(R.id.tv_userInfo);

        initView();
        lv_userInfo = findViewById(R.id.lv_userInfo);
        //loadUserInfo();
    }

    private void initView() {
        entityUserDao = new EntityUserDao();
        mainHandler = new Handler(getMainLooper());//获取主线程
    }

    /*
     *//*填充userInfo列表*//*
    private void loadUserInfo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent intent = getIntent();
                String userNameForInfo = intent.getStringExtra("passValue2");//登陆后的传值
                userInfoList = entityUserDao.getUserInfoListByid(userNameForInfo);
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        showUserInfoLvData();
                    }
                });
            }
        }).start();
    }

    private void showUserInfoLvData() {
        if (addressAdapter == null) {
            addressAdapter = new AddressAdapter(this, addressList);
            lv_address.setAdapter(addressAdapter);
        } else {
            addressAdapter.setAddressList(addressList);
            addressAdapter.notifyDataSetChanged();
        }


    }*/
}