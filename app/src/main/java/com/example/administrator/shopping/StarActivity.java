package com.example.administrator.shopping;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.administrator.shopping.Impl.OnDelBtnClickListener;
import com.example.administrator.shopping.Impl.OnEditBtnClickListener;
import com.example.administrator.shopping.adapter.ReceivedAdapter;
import com.example.administrator.shopping.adapter.StarAdapter;
import com.example.administrator.shopping.dao.OrderDao;
import com.example.administrator.shopping.entity.AddressEntity;
import com.example.administrator.shopping.entity.OrderEntity;

import java.io.Serializable;
import java.util.List;

public class StarActivity extends AppCompatActivity {


    private ImageView go_back;
    private Button btn_pingjia;

    /*商品列表*/
    private ListView lv_finishList;
    private OrderDao orderDao;
    private List<OrderEntity> orderList;
    private StarAdapter starAdapter;

    private Handler mainHandler;     // 主线程

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star);

        SettingActivity.activityList.add(this);//用来退出

        Intent intent = getIntent();
        final String passValue = intent.getStringExtra("passValue");//登陆后的传值

        go_back = findViewById(R.id.go_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_pingjia = findViewById(R.id.btn_pingjia);

        /*填充列表*/
        lv_finishList = findViewById(R.id.lv_finishList);
        loadstarDb();

        initView();

    }


    private void initView() {
        mainHandler = new Handler(getMainLooper());//获取主线程
        orderDao = new OrderDao();

    }


    /*填充商品列表*/
    private void loadstarDb() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent intent = getIntent();
                final String username = intent.getStringExtra("passValue");//登陆后的传值
                Log.i("TAG", "run: " + username);
                orderList = orderDao.getFinishList(username);
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
        if (starAdapter == null) {
            starAdapter = new StarAdapter(this, orderList);
            lv_finishList.setAdapter(starAdapter);
        } else {
            starAdapter.setOrderList(orderList);
            starAdapter.notifyDataSetChanged();
        }

        starAdapter.setOnDelBtnClickListener(new OnDelBtnClickListener() {
            @Override
            public void onDelBtnClick(View view, int position) {
                Intent intent = getIntent();
                final String passValue = intent.getStringExtra("passValue");//登陆后的传值
                OrderEntity item = orderList.get(position);

                Bundle bundle = new Bundle();
                bundle.putSerializable("descriptionEdit", item);
                intent = new Intent(StarActivity.this, SetpingjiaActivity.class);
                intent.putExtra("passValus", passValue);
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);

               /* intent = new Intent(StarActivity.this, SetpingjiaActivity.class);
                intent.putExtra("passValue", passValue);//传递“id”至ShoppingCartActivity
                startActivity(intent);
                //doConformOrder(item.getUuid());*/
            }
        });


    }
}