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
    private TextView tvAddress;
    private TextView tv_Update;
    private TextView tv_share;
    private TextView tv_userName;
    private TextView tv_wallet;

    private String Name;
    private Handler mainHandler;     // 主线程
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            //super.handleMessage(msg);
            if(msg.what==0){
                String walletgeneral = (String) msg.obj;
                if (walletgeneral==null){
                    tv_wallet.setText("0.00");
                }else
                    tv_wallet.setText(walletgeneral);
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

        tv_userName.setText("ID:    " + userName2);

        mainHandler = new Handler(getMainLooper());
        /*ImageView back = (ImageView) findViewById(R.id.go_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyActivity.this, ShopActivity.class);
                startActivityForResult(intent, 1);
            }
        });*/

        //跳转至slqite首页
     /*   imgHome = findViewById(R.id.img_home);
        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(MyActivity.this, ShopActivity.class);
                startActivity(intent);
            }
        });*/

        /*我的*/
        imgMy = findViewById(R.id.img_my);
        imgMy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(MyActivity.this, MyActivity.class);
                startActivity(intent);
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
        /*sqlite购物车*/
        /*imgCart = findViewById(R.id.img_cart);
        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(MyActivity.this, ShoplistActivity.class);
                intent.putExtra("passValueForCart", userName2);//传递“id”至ShoppingCartActivity
                startActivity(intent);
            }
        });*/
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
        /*登录*/
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
                    intent.putExtra("passValueForUser", userName2);//传递“id”至ShoppingCartActivity
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
                startActivity(intent);
            }
        });


        /*钱包*/
        tv_wallet=findViewById(R.id.tv_wallet);

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

        tv_Update=findViewById(R.id.tv_update);
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

        tv_share=findViewById(R.id.tv_share);
        tv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareUntils.shareText(MyActivity.this,"2","分享");
            }
        });

        doQueryWallet();
    }


    // 执行查询用户数量的方法
    private void doQueryWallet(){
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
    @Override
    public void onClick(View view) {

    }


}


