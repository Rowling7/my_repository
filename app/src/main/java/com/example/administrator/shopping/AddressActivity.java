package com.example.administrator.shopping;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.shopping.adapter.EntityUserAdapter;
import com.example.administrator.shopping.dao.EntityUserDao;
import com.example.administrator.shopping.dao.GoodsDao;
import com.example.administrator.shopping.entity.EntityUserEntity;

import java.util.List;


public class AddressActivity extends AppCompatActivity implements View.OnClickListener {

    private Handler handler;
    /*地址列表*/
    private ListView lv_address;
    private TextView tv_address;
    private EntityUserDao entityUserDao;
    private List<EntityUserEntity> addressList;
    private EntityUserAdapter entityUserAdapter;

    private Handler mainHandler;     // 主线程


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        SettingActivity.activityList.add(this);

        initView();
        lv_address=findViewById(R.id.lv_address);
        loadAddress();
    }

    private void initView() {
        mainHandler = new Handler(getMainLooper());//获取主线程
        entityUserDao = new EntityUserDao();
    }

    /*填充地址列表*/
    private void loadAddress() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                addressList = entityUserDao.getAddressListByid();
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        showAddressLvData();
                    }
                });
            }
        }).start();
    }

    private void showAddressLvData() {
        if (entityUserAdapter == null) {
            entityUserAdapter = new EntityUserAdapter(this, addressList);
            lv_address.setAdapter(entityUserAdapter);
        } else {
            entityUserAdapter.setAddressList(addressList);
            entityUserAdapter.notifyDataSetChanged();
        }
    }
    @Override
    public void onClick(View view) {

    }

}