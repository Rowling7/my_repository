package com.example.administrator.shopping;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.shopping.Impl.OnDelBtnClickListener;
import com.example.administrator.shopping.Impl.OnLessBtnClickListener;
import com.example.administrator.shopping.adapter.ShoppingCartAdapter;
import com.example.administrator.shopping.dao.AddressDao;
import com.example.administrator.shopping.dao.EntityUserDao;
import com.example.administrator.shopping.dao.GoodsDao;
import com.example.administrator.shopping.dao.OrderDao;
import com.example.administrator.shopping.dao.ShoppingCartDao;
import com.example.administrator.shopping.entity.EntityUserEntity;
import com.example.administrator.shopping.entity.OrderEntity;
import com.example.administrator.shopping.entity.ShoppingCartEntity;
import com.example.administrator.shopping.utils.Myuntils;
import com.example.administrator.shopping.utils.ToastUtils;

import java.util.List;

public class SettlementActivity extends AppCompatActivity {

    private Button btn_orderAndPay;
    private TextView tv_allPrice;
    private TextView tv_cartCount;
    private ImageView go_back;
    private ImageView iv_received;
    private OrderDao orderDao;
    private TextView tv_phone;
    private Spinner s_select;
    public static final String TAG = "OUTPUT";
    private Handler mainHandler;// 主线程
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
            } else if (msg.what == 2) {
                String uPhone = (String) msg.obj;
                tv_phone.setText(uPhone);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settlement);

        SettingActivity.activityList.add(this);

        Intent intent = getIntent();
        final String username2 = intent.getStringExtra("passValue");//MyActivity的传值

        tv_allPrice = findViewById(R.id.tv_allPrice);
        tv_cartCount = findViewById(R.id.tv_cartCount);
        tv_phone = findViewById(R.id.tv_phone);

        go_back = findViewById(R.id.go_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        iv_received = findViewById(R.id.iv_received);
        iv_received.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                final String userName2 = intent.getStringExtra("passValue");
                intent = new Intent(SettlementActivity.this, ReceivedActivity.class);
                intent.putExtra("passValue", userName2);
                startActivity(intent);
            }
        });

        //   btn_pay = findViewById(R.id.btn_pay);

        btn_orderAndPay = findViewById(R.id.btn_orderAndPay);

        Spinner s_select = findViewById(R.id.s_select);
        // 建立数据源
        String[] mItems = {"广西南宁西乡塘区中华路82号中华路82号", "广西南宁西乡塘区中华路60号中华路60号", "广西百色右江区站前大道103-10号", "广西贵港建设路与凤凰一街交叉路口东北侧(丰宝王府井广场西侧)"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        s_select.setAdapter(adapter);

        Log.i(TAG, "onCreate: " + tv_cartCount);
        Log.i(TAG, "onCreate: " + tv_allPrice);
        doQueryCount();
        doCount();
        selectPhone();
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

    // 查询phone
    public void selectPhone() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent intent = getIntent();
                final String userNameForDetails = intent.getStringExtra("passValue");//MyActivity的传值
                String phone = EntityUserDao.getUserPhone(userNameForDetails);
                Message msg = Message.obtain();
                msg.what = 2;   // 查询结果
                msg.obj = phone;
                // 向主线程发送数据
                handler.sendMessage(msg);

            }
        }).start();
    }

    public void btn_on_click_forPay(View view) {
        final String goodsCount = tv_cartCount.getText().toString().trim();
        final String goodsPrice = tv_allPrice.getText().toString().trim();
        final String datetime = Myuntils.getDateStrFromNow();
        final Intent intent = getIntent();
        final String username = intent.getStringExtra("passValue");//登陆后的传值
        final OrderEntity orderEntity = new OrderEntity();
        orderEntity.setGoodscount(goodsCount);
        orderEntity.setGoodsprice(goodsPrice);
        orderEntity.setDatetime(datetime);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Log.i(TAG, "run: " + goodsCount);
                Log.i(TAG, "run: " + goodsPrice);
                Log.i(TAG, "run: " + datetime);
                OrderDao.insOrder(goodsCount, goodsPrice, username, datetime);
                ToastUtils.showMsg(SettlementActivity.this, "订单已创建，正在前往支付");
                final PayPasswordDialog dialog = new PayPasswordDialog(SettlementActivity.this, R.style.mydialog);
                dialog.setDialogClick(new PayPasswordDialog.DialogClick() {
                    @Override
                    public void doConfirm(String password) {
                        dialog.dismiss();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // Looper.perpare();
                                Log.i(TAG, "run: " + username);
                                String isnull = EntityUserDao.confWallet(goodsPrice, username);
                                if (isnull.equals(0 + "")) {
                                    Intent intent = null;
                                    EntityUserDao.reConfWallet(goodsPrice, username);
                                    intent = new Intent(SettlementActivity.this,WalletActivity.class);
                                    intent.putExtra("passValue", username);
                                    startActivity(intent);
                                    ToastUtils.showMsg(SettlementActivity.this, "余额不足无法完成支付！");
                                } else {
                                    OrderDao.updateStatus(username);//更新订单
                                    EntityUserDao.updateWallet(goodsPrice, username);//更新钱包余额
                                    ToastUtils.showMsg(SettlementActivity.this, "支付完成,可在待收货中查看");
                                    ShoppingCartDao.delAllCart(username);//清空购物车
                                    GoodsDao.updateNumber();//更新goods数量
                                    Intent intent = getIntent();
                                    final String userName2 = intent.getStringExtra("passValue");
                                    intent = new Intent(SettlementActivity.this, MainActivity.class);
                                    intent.putExtra("passValueForMain", userName2);
                                    startActivity(intent);
                                }
                            }
                        }).start();
                    }
                });
                dialog.show();
                Looper.loop();
            }
        }).start();
    }

}



