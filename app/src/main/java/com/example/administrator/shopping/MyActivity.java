package com.example.administrator.shopping;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/*import com.example.administrator.shopping.dao.EntityUserDao;
import com.example.administrator.shopping.Entity.EntityUserEntity;*/
import com.example.administrator.shopping.utils.ToastUtils;

/*import com.example.administrator.shopping.utils.CommonUtils;*/

public class MyActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgMy;
    private ImageView imgHome;
    private ImageView imgCart;
    private ImageView imgLogin;
    private ImageView imgSetting;
    private TextView tvAddress;
    private TextView tvUpdate;
    public Button btn_login;
    private TextView tv_userName;

    private String Name;
    private Handler mainHandler;     // 主线程
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            //super.handleMessage(msg);
            if (msg.what == 0) {
                int count = (Integer) msg.obj;
                tv_userName.setText("" + Name);
            }
        }
    };

    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        SettingActivity.activityList.add(this);

        initView();
        listView = (ListView) findViewById(R.id.listview);
        ImageView back = (ImageView) findViewById(R.id.go_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyActivity.this, ShopActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        imgMy = findViewById(R.id.img_my);
        imgMy.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(MyActivity.this, MyActivity.class);
                startActivity(intent);
            }
        });
        imgHome = findViewById(R.id.img_home);
        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(MyActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        imgCart = findViewById(R.id.img_cart);
        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(MyActivity.this, ShoplistActivity.class);
                startActivity(intent);
            }
        });

        imgLogin = findViewById(R.id.img_login);
        imgLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(MyActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        imgSetting = findViewById(R.id.img_settings);
        imgSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(MyActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });

        tvAddress = findViewById(R.id.tv_address);
        tvAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(MyActivity.this, AddressActivity.class);
                startActivity(intent);
            }
        });

        /*tvUpdate = findViewById(R.id.tv_update);
        tvUpdate.setOnClickListener(new View.OnClickListener() { //弹出消息
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

       /* switch (v.getId()) {
            case R.id.btn_Login:    // LOGIN页面的登录按钮
                doGetName();
                break;
        }*/


    private void initView() {
        SettingActivity.activityList.add(this);
        mainHandler = new Handler(getMainLooper());//获取主线程
    }

    @Override
    public void onClick(View view) {

    }


    /*private void doGetName() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final EntityUserEntity userName = EntityUserDao.getUserName(Name);
                Message msg = Message.obtain();
                msg.what = 0;   // 查询结果
                msg.obj = userName;
                // 向主线程发送数据
                handler.sendMessage(msg);
            }
        }).start();
    }*/

}

