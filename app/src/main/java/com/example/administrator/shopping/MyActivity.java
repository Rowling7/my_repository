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
import com.example.administrator.shopping.utils.ToastUtils;

public class MyActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgMy;
    private ImageView imgHome;
    private ImageView imgCart;
    private ImageView imgLogin;
    private ImageView imgSetting;
    private TextView tvAddress;
    private TextView tv_Update;
    private TextView tv_userName;

    private String Name;
    private Handler mainHandler;     // 主线程

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        SettingActivity.activityList.add(this);//用来退出

        tv_userName = findViewById(R.id.tv_userName);
        Intent intent = getIntent();
        final String userName2 = intent.getStringExtra("passValue");//登陆后的传值
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
                Intent intent = null;
                intent = new Intent(MyActivity.this, LoginActivity.class);
                startActivity(intent);
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

       /* tv_Update.findViewById(R.id.tv_update);
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
        });*/

    }

    @Override
    public void onClick(View view) {

    }


}


