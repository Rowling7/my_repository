package com.example.administrator.shopping;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.administrator.shopping.Impl.OnInsBtnClickListener;
import com.example.administrator.shopping.adapter.GoodsAdapter;
import com.example.administrator.shopping.adapter.ReceivedAdapter;
import com.example.administrator.shopping.dao.GoodsDao;
import com.example.administrator.shopping.dao.OrderDao;
import com.example.administrator.shopping.entity.GoodsEntity;
import com.example.administrator.shopping.entity.OrderEntity;

import java.util.List;

public class ReceivedActivity extends AppCompatActivity {

    private ImageView go_back;

    /*商品列表*/
    private ListView lv_orderList;
    private OrderDao orderDao;
    private List<OrderEntity> orderList;
    private ReceivedAdapter receivedAdapter;

    private Handler mainHandler;     // 主线程


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received);
        SettingActivity.activityList.add(this);//用来退出应用
        initView();


        go_back = findViewById(R.id.go_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //Intent intent = getIntent();
        // final String userNameForMain = intent.getStringExtra("passValue");//MyActivity的传值

        /*填充列表*/
        lv_orderList = findViewById(R.id.lv_orderList);
        loadOrderDb();

    }

    private void initView() {
        mainHandler = new Handler(getMainLooper());//获取主线程
        orderDao = new OrderDao();

    }


    /*填充商品列表*/
    private void loadOrderDb() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent intent = getIntent();
                final String username = intent.getStringExtra("passValue");//登陆后的传值
                orderList = orderDao.getOrderList(username);
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        showLvData();
                    }
                });
            }
        }).start();
    }

    private void showLvData() {
        if (receivedAdapter == null) {
            receivedAdapter = new ReceivedAdapter(this, orderList);
            lv_orderList.setAdapter(receivedAdapter);
        } else {
            receivedAdapter.setOrderList(orderList);
            receivedAdapter.notifyDataSetChanged();
        }

    }
}