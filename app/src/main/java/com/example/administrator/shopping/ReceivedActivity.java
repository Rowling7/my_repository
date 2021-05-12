package com.example.administrator.shopping;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.shopping.Impl.OnDelBtnClickListener;
import com.example.administrator.shopping.Impl.OnEditBtnClickListener;
import com.example.administrator.shopping.Impl.OnInsBtnClickListener;
import com.example.administrator.shopping.adapter.GoodsAdapter;
import com.example.administrator.shopping.adapter.ReceivedAdapter;
import com.example.administrator.shopping.dao.EntityUserDao;
import com.example.administrator.shopping.dao.GoodsDao;
import com.example.administrator.shopping.dao.OrderDao;
import com.example.administrator.shopping.entity.AddressEntity;
import com.example.administrator.shopping.entity.GoodsEntity;
import com.example.administrator.shopping.entity.OrderEntity;
import com.example.administrator.shopping.utils.ToastUtils;

import java.util.List;

public class ReceivedActivity extends AppCompatActivity {

    private ImageView go_back;
    private TextView tv_kefu;

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

                Intent intent = getIntent();
                final String userName = intent.getStringExtra("passValue");//登陆后的传值
                String numbForNoReceived = EntityUserDao.getNumbForReceived(userName);
                String numbForNopay = EntityUserDao.getNumbForNopay(userName);
                intent = new Intent(ReceivedActivity.this, MyActivity.class);
                intent.putExtra("passValue", userName);
                intent.putExtra("numbForNoReceived", numbForNoReceived);
                intent.putExtra("numbForNopay", numbForNopay);
                startActivity(intent);
                // finish();
            }
        });
        //Intent intent = getIntent();
        // final String userNameForMain = intent.getStringExtra("passValue");//MyActivity的传值

        tv_kefu = findViewById(R.id.tv_kefu);


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

        receivedAdapter.setOnDelBtnClickListener(new OnDelBtnClickListener() {
            @Override
            public void onDelBtnClick(View view, int position) {
                final OrderEntity item = orderList.get(position);
                doConformOrder(item.getUuid());
            }
        });


        // 修改按钮的操作
        receivedAdapter.setOnEditBtnClickListener(new OnEditBtnClickListener() {
            @Override
            public void onEditBtnClick(View view, int position) {
                Intent intent = getIntent();
                final String passValue = intent.getStringExtra("passValue");//登陆后的传值
                // 修改按钮的操作
                OrderEntity item = orderList.get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("userEdit", item);
                intent = new Intent(ReceivedActivity.this, KefuActivity.class);
                intent.putExtra("passValue", passValue);
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
            }
        });


    }


    //执行删除购物车中的数据
    private void doConformOrder(final long uuid) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final int iRow = OrderDao.ConOrder(uuid);
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showMsg(ReceivedActivity.this, "已确认收货");
                        loadOrderDb();// 重新加载数据
                    }
                });
            }
        }).start();
    }
}