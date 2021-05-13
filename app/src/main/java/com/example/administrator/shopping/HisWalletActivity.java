package com.example.administrator.shopping;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.shopping.adapter.HisWalletAdapter;
import com.example.administrator.shopping.dao.OrderDao;
import com.example.administrator.shopping.entity.OrderEntity;

import java.util.List;

public class HisWalletActivity extends AppCompatActivity {

    private TextView tv_goodsPrice2;
    private TextView tv_time2;
    private ImageView go_back;


    /*地址列表*/
    private ListView lv_hisWallet;
    private OrderDao orderDao;
    private List<OrderEntity> hisWalletList;
    private HisWalletAdapter hisWalletAdapter;

    private Handler mainHandler;     // 主线程

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_his_wallet);
        SettingActivity.activityList.add(this);//用来退出
        initView();

        go_back = findViewById(R.id.go_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();
        final String passValue = intent.getStringExtra("passValue");//登陆后的传值

        tv_goodsPrice2 = findViewById(R.id.tv_goodsPrice2);
        tv_time2 = findViewById(R.id.tv_time2);
        lv_hisWallet = findViewById(R.id.lv_hisWallet);
        loadHisWallet();

    }

    private void initView() {
        mainHandler = new Handler(getMainLooper());//获取主线程
        orderDao = new OrderDao();
    }

    /*填充地址列表*/
    private void loadHisWallet() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent intent = getIntent();
                String username = intent.getStringExtra("passValue");//登陆后的传值
                Log.i("TAG", "run: " + username);
                hisWalletList = orderDao.getHisWallet(username);
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        showHisWalletLvData();
                    }
                });
            }
        }).start();
    }

    private void showHisWalletLvData() {
        if (hisWalletAdapter == null) {
            hisWalletAdapter = new HisWalletAdapter(this, hisWalletList);
            lv_hisWallet.setAdapter(hisWalletAdapter);
        } else {
            hisWalletAdapter.setHisWalletList(hisWalletList);
            hisWalletAdapter.notifyDataSetChanged();
            Log.i("TAG", "showAddressLvData: " + hisWalletList);
        }
    }

}