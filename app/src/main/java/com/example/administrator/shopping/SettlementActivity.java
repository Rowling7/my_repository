package com.example.administrator.shopping;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.shopping.dao.EntityUserDao;
import com.example.administrator.shopping.dao.ShoppingCartDao;
import com.example.administrator.shopping.entity.EntityUserEntity;
import com.example.administrator.shopping.utils.ToastUtils;

public class SettlementActivity extends AppCompatActivity {

    private Button btn_pay;
    private TextView tv_allPrice;
    private TextView tv_cartCount;
    private ImageView go_back;
    public static final String TAG = "OUTPUT";
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            //super.handleMessage(msg);
            if (msg.what == 0) {
                String sum = (String) msg.obj;
                if (sum == null) {
                    tv_allPrice.setText("¥ 0");

                } else
                    tv_allPrice.setText("¥ " + sum);
            } else if (msg.what == 1) {
                String cartCount = (String) msg.obj;
                if (cartCount == null) {
                    tv_cartCount.setText(" 0 种");

                } else
                    tv_cartCount.setText(cartCount + "种");
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settlement);

        SettingActivity.activityList.add(this);

        btn_pay = findViewById(R.id.btn_pay);
        tv_allPrice = findViewById(R.id.tv_allPrice);
        tv_cartCount = findViewById(R.id.tv_cartCount);

        go_back = findViewById(R.id.go_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_pay = findViewById(R.id.btn_pay);
        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  doins
                ToastUtils.showMsg(SettlementActivity.this, "正在前往支付页面~");
            }
        });

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
                final String userNameForCart = intent.getStringExtra("passValueForpay");//MyActivity的传值
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
                final String userNameForCart = intent.getStringExtra("passValueForpay");//MyActivity的传值
                String count = ShoppingCartDao.getCartSum(userNameForCart);
                Message msg = Message.obtain();
                msg.what = 0;   // 查询结果
                msg.obj = count;

                // 向主线程发送数据
                handler.sendMessage(msg);
            }
        }).start();

    }


   /* btn_pay.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final PayPasswordDialog dialog = new PayPasswordDialog(WalletActivity.this, R.style.mydialog);
            dialog.setDialogClick(new PayPasswordDialog.DialogClick() {
                @Override
                public void doConfirm(String password) {

                    final String etjine = et_jine.getText().toString().trim();
                    final String jine = tv_money.getText().toString().trim();
                    Log.i(TAG, "doConfirm: "+jine);
                    final EntityUserEntity entityUserEntity = new EntityUserEntity();
                    entityUserEntity.setWallet(jine);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = getIntent();
                            final String userName = intent.getStringExtra("passValue");//登陆后的传值
                            final int iRow = EntityUserDao.addWallet(userName,jine);
                            dialog.dismiss();
                            intent = new Intent(WalletActivity.this, MyActivity.class);
                            doQueryWallet();
                            ToastUtils.showMsg(WalletActivity.this,"充值成功");

                            finish();
                              *//*  mainHandler.post(new Runnable() {
                                    @Override
                                    public void run() {


                                    }
                                });*//*
                        }
                    }).start();



                }
            });
            dialog.show();
        }
    });*/


}

