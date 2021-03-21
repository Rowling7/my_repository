package com.example.administrator.shopping;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.shopping.dao.AddressDao;
import com.example.administrator.shopping.dao.EntityUserDao;
import com.example.administrator.shopping.dao.OrderDao;
import com.example.administrator.shopping.dao.ShoppingCartDao;
import com.example.administrator.shopping.entity.EntityUserEntity;
import com.example.administrator.shopping.entity.OrderEntity;
import com.example.administrator.shopping.utils.Myuntils;
import com.example.administrator.shopping.utils.ToastUtils;

public class SettlementActivity extends AppCompatActivity {

    private Button btn_orderAndPay;
    private TextView tv_allPrice;
    private TextView tv_cartCount;
    private ImageView go_back;
    private OrderDao orderDao;
    public static final String TAG = "OUTPUT";
    private Handler mainHandler;     // 主线程
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            //super.handleMessage(msg);
            if (msg.what == 0) {
                String sum = (String) msg.obj;
                if (sum == null) {
                    tv_allPrice.setText("0");

                } else
                    tv_allPrice.setText(sum);
            } else if (msg.what == 1) {
                String cartCount = (String) msg.obj;
                if (cartCount == null) {
                    tv_cartCount.setText("0");

                } else
                    tv_cartCount.setText(cartCount);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settlement);

        SettingActivity.activityList.add(this);

        btn_orderAndPay = findViewById(R.id.btn_orderAndPay);
        tv_allPrice = findViewById(R.id.tv_allPrice);
        tv_cartCount = findViewById(R.id.tv_cartCount);

        go_back = findViewById(R.id.go_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //   btn_pay = findViewById(R.id.btn_pay);
        /*btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                doInsOrder();
            }
        });*/

        Log.i(TAG, "onCreate: " + tv_cartCount);
        Log.i(TAG, "onCreate: " + tv_allPrice);
        doQueryCount();
        doCount();
    }


    private void doCount() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent intent = getIntent();
                final String userNameForCart = intent.getStringExtra("passValue");//MyActivity的传值
                String count = ShoppingCartDao.getCartCount(userNameForCart);
                Message msg = Message.obtain();
                msg.what = 1;   // 查询结果
                msg.obj = count;

                // 向主线程发送数据
                handler.sendMessage(msg);
            }
        }).start();
    }

    // 查询购物车总金额
    public void doQueryCount() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent intent = getIntent();
                final String userNameForCart = intent.getStringExtra("passValue");//MyActivity的传值
                String count = ShoppingCartDao.getCartSum(userNameForCart);
                Message msg = Message.obtain();
                msg.what = 0;   // 查询结果
                msg.obj = count;

                // 向主线程发送数据
                handler.sendMessage(msg);
            }
        }).start();

    }

    public void btn_on_click_forPay(View view) {
        final String goodsCount = tv_cartCount.getText().toString().trim();
        final String goodsPrice = tv_allPrice.getText().toString().trim();
        final String datetime = Myuntils.getDateStrFromNow();
        Intent intent = getIntent();
        final String username = intent.getStringExtra("passValue");//登陆后的传值

        final OrderEntity orderEntity = new OrderEntity();
        orderEntity.setGoodscount(goodsCount);
        orderEntity.setGoodsprice(goodsPrice);
        orderEntity.setDatetime(datetime);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "run: " + goodsCount);
                Log.i(TAG, "run: " + goodsPrice);
                Log.i(TAG, "run: " + datetime);
                orderDao.insOrder(goodsCount, goodsPrice, username, datetime);
                ToastUtils.showMsg(SettlementActivity.this, "已创建新的订单，即将支付");
                Message msg = Message.obtain();
                msg.what = 99;
                handler.sendMessage(msg);
                /*mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showMsg(SettlementActivity.this, "已创建新的订单，即将支付");
                        setResult(1);
                        //finish();
                    }
                });*/
            }
        }).start();
    }
}



