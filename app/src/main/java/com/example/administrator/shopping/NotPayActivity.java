package com.example.administrator.shopping;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.administrator.shopping.Impl.OnInsBtnClickListener;
import com.example.administrator.shopping.adapter.GoodsAdapter;
import com.example.administrator.shopping.adapter.OrderAdapter;
import com.example.administrator.shopping.dao.GoodsDao;
import com.example.administrator.shopping.dao.OrderDao;
import com.example.administrator.shopping.entity.GoodsEntity;
import com.example.administrator.shopping.entity.OrderEntity;
import com.example.administrator.shopping.utils.ToastUtils;

import java.util.List;

public class NotPayActivity extends AppCompatActivity {

    private Button btn_delorder;
    private Button btn_gopay;
    private ImageView go_back;

    /*订单列表*/
    private ListView lv_noPayGoods;
    private OrderDao orderDao;
    private List<OrderEntity> orderList;
    private OrderAdapter orderAdapter;

    private Handler mainHandler;     // 主线程
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notpay);
        Intent intent = getIntent();
        final String userName2 = intent.getStringExtra("passValue");//登陆后的传值

        SettingActivity.activityList.add(this);//用来退出应用
        initView();

        go_back = findViewById(R.id.go_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_delorder=findViewById(R.id.btn_delorder);
        btn_gopay=findViewById(R.id.btn_gopay);

        /*填充列表*/
        lv_noPayGoods = findViewById(R.id.lv_noPayGoods);
        //loadOrderDb();
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
               // orderList = OrderDao.getAllOrderList();
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
        if (orderAdapter == null) {
            orderAdapter = new OrderAdapter(this,orderList);
            lv_noPayGoods.setAdapter(orderAdapter);
        } else {
            orderAdapter.setOrderList(orderList);
            orderAdapter.notifyDataSetChanged();
        }

        // 添加按钮的操作
        orderAdapter.setOnInsBtnClickListener(new OnInsBtnClickListener() {
            @Override
            public void OnInsBtnClick(View view, int position) {
                //  方法
                final OrderEntity item = orderList.get(position);
                //doInsCart(item.getUuid());
            }
        });
    }


}