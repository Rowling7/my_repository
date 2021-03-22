package com.example.administrator.shopping;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/*import com.example.administrator.shopping.dao.EntityUserDao;
import com.example.administrator.shopping.Entity.EntityUserEntity;*/
import com.example.administrator.shopping.dao.EntityUserDao;
import com.example.administrator.shopping.dao.ShoppingCartDao;
import com.example.administrator.shopping.utils.ShareUntils;
import com.example.administrator.shopping.utils.ToastUtils;

public class MyActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgMy;
    private ImageView imgHome;
    private ImageView imgCart;
    private ImageView imgLogin;
    private ImageView imgSetting;
    private ImageView iv_received;
    private TextView tvAddress;
    private TextView tv_Update;
    private TextView tv_share;
    private TextView tv_userName;
    private TextView tv_wallet;
    private ImageView iv_star;
    private ImageView iv_noPay;
    private ImageView go_back;
    private TextView tv_numbForNopay;
    private TextView tv_numbForReceived;

    private String Name;
    private Handler mainHandler;     // 主线程
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            //super.handleMessage(msg);
            if (msg.what == 0) {
                String walletgeneral = (String) msg.obj;
                if (walletgeneral == null) {
                    tv_wallet.setText("0.00");
                } else
                    tv_wallet.setText(walletgeneral);
            } else if (msg.what == 1) {
                String realName = (String) msg.obj;
                tv_userName.setText(realName);
            }else if (msg.what == 2) {
                String numbForNopay = (String) msg.obj;
                if (numbForNopay.equals("0")) {
                    tv_numbForNopay.setText("");
                } else
                tv_numbForNopay.setText(numbForNopay);
            }else if (msg.what == 3) {
                String numbForNoReceived = (String) msg.obj;
                if (numbForNoReceived.equals("0")) {
                    tv_numbForReceived.setText("");
                } else

                tv_numbForReceived.setText(numbForNoReceived);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        SettingActivity.activityList.add(this);//用来退出

        tv_userName = findViewById(R.id.tv_userName);
        Intent intent = getIntent();
        final String userName2 = intent.getStringExtra("passValue");//登陆后的传值
        final String islogin = intent.getStringExtra("Islogin");//登陆后的传值


        mainHandler = new Handler(getMainLooper());

        go_back = findViewById(R.id.go_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /*主页*/
        imgHome = findViewById(R.id.img_home);
        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(MyActivity.this, MainActivity.class);
                intent.putExtra("passValueForMain", userName2);//传递“id”至MainActivity
                startActivity(intent);
            }
        });

        /*mysql购物车*/
        imgCart = findViewById(R.id.img_cart);
        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(MyActivity.this, ShoppingCartActivity.class);
                intent.putExtra("passValueForCart", userName2);//传递“id”至ShoppingCartActivity
                startActivity(intent);
            }
        });
        /*我的详细信息*/
        imgLogin = findViewById(R.id.img_login);
        imgLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userName2 == null) {
                    Intent intent = null;
                    intent = new Intent(MyActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = null;
                    intent = new Intent(MyActivity.this, MyDetailsActivity.class);
                    intent.putExtra("passValueForUser", userName2);//传递“id”至MyDetailsActivity
                    startActivity(intent);
                }

            }
        });
        /*设置*/
        imgSetting = findViewById(R.id.img_settings);
        imgSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(MyActivity.this, SettingActivity.class);
                intent.putExtra("passValueForSet", userName2);//传递“id”至MainActivity
                startActivity(intent);
            }
        });

        //待付款
        iv_noPay = findViewById(R.id.iv_noPay);
        iv_noPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(MyActivity.this, NotPayActivity.class);
                intent.putExtra("passValue", userName2);//传递“id”至MainActivity
                startActivity(intent);
            }
        });

        tv_numbForNopay=findViewById(R.id.tv_numbForNopay);


        //待收货
        iv_received = findViewById(R.id.iv_received);
        iv_received.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(MyActivity.this, ReceivedActivity.class);
                intent.putExtra("passValue", userName2);//传递“id”至MainActivity
                startActivity(intent);
            }
        });

        tv_numbForReceived =findViewById(R.id.tv_numbForReceived);

        /*评价*/
        iv_star = findViewById(R.id.iv_star);
        iv_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(MyActivity.this, StarActivity.class);
                intent.putExtra("passValue", userName2);//传递“id”至ShoppingCartActivity
                startActivity(intent);
            }
        });


        /*钱包*/
        tv_wallet = findViewById(R.id.tv_wallet);
        tv_wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(MyActivity.this, WalletActivity.class);
                intent.putExtra("passValue", userName2);//传递“id”至MainActivity
                doQueryWallet();
                startActivity(intent);
            }
        });

        /*地址管理*/
        tvAddress = findViewById(R.id.tv_addressManager);
        tvAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(MyActivity.this, AddressActivity.class);
                intent.putExtra("passValue2", userName2);
                startActivity(intent);
            }
        });
        /*伪更新*/
        tv_Update = findViewById(R.id.tv_update);
        tv_Update.setOnClickListener(new View.OnClickListener() { //弹出消息
            @Override
            public void onClick(View view) {
                ToastUtils.showMsg(MyActivity.this, "正在检查更新！");
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showMsg(MyActivity.this, "已是最新版本！");
                    }
                }, 3000);
            }
        });
        /*分享*/
        tv_share = findViewById(R.id.tv_share);
        tv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareUntils.shareText(MyActivity.this, "2", "分享");
            }
        });

        doQueryWallet();
        doQueryrealName();
        doQueryNumbForNopay();
        doQueryNumbForReceived();
    }


    // 执行查询钱包的方法
    public void doQueryWallet() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                Intent intent = getIntent();
                final String userName2 = intent.getStringExtra("passValue");//登陆后的传值
                String userWallet = EntityUserDao.getUserWallet(userName2);

                Message msg = Message.obtain();
                msg.what = 0;   // 查询结果
                msg.obj = userWallet;
                // 向主线程发送数据
                handler.sendMessage(msg);
            }

        }).start();
    }

    // 执行查询用户数量的方法
    private void doQueryrealName() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                Intent intent = getIntent();
                final String userName2 = intent.getStringExtra("passValue");//登陆后的传值
                String realName = EntityUserDao.getUserRealName(userName2);

                Message msg = Message.obtain();
                msg.what = 1;   // 查询结果
                msg.obj = realName;
                // 向主线程发送数据
                handler.sendMessage(msg);
            }

        }).start();
    }

    // 执行查询待付款的方法
    public void doQueryNumbForNopay() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                Intent intent = getIntent();
                final String userName = intent.getStringExtra("passValue");//登陆后的传值
                String numbForNopay = EntityUserDao.getNumbForNopay(userName);

                Message msg = Message.obtain();
                msg.what = 2;   // 查询结果
                msg.obj = numbForNopay;
                // 向主线程发送数据
                handler.sendMessage(msg);
            }

        }).start();
    }

    // 执行查询待收货的方法
    public void doQueryNumbForReceived () {
        new Thread(new Runnable() {

            @Override
            public void run() {
                Intent intent = getIntent();
                final String userName = intent.getStringExtra("passValue");//登陆后的传值
                String numbForNoReceived = EntityUserDao.getNumbForReceived(userName);

                Message msg = Message.obtain();
                msg.what = 3;   // 查询结果
                msg.obj = numbForNoReceived;
                // 向主线程发送数据
                handler.sendMessage(msg);
            }

        }).start();
    }

    @Override
    public void onClick(View view) {

    }


}


