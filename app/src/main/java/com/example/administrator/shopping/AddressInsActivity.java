package com.example.administrator.shopping;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.administrator.shopping.dao.AddressDao;
import com.example.administrator.shopping.dao.EntityUserDao;
import com.example.administrator.shopping.entity.EntityUserEntity;
import com.example.administrator.shopping.utils.ToastUtils;

public class AddressInsActivity extends AppCompatActivity {

    private Button btn_insAddress;
    private EditText et_addressEdited;
    private EditText et_userName;
    private EntityUserEntity addressEdit;//用户要修改的地址
    private EntityUserDao entityUserDao;
    private Handler mainHandler;     // 主线程
    private ImageView go_back;
    private final Handler handler = new Handler() {
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_ins);
        SettingActivity.activityList.add(this);//用来退出应用

        btn_insAddress = findViewById(R.id.btn_insAddress);
        et_addressEdited = findViewById(R.id.et_addressEdited);
        et_userName = findViewById(R.id.et_userName);

        initView();
        go_back = findViewById(R.id.go_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        SettingActivity.activityList.add(this);
        mainHandler = new Handler(getMainLooper());//获取主线程
        Intent intent = getIntent();
        final String userNameInsAd = intent.getStringExtra("passValueForInsAd");//登陆后的传值
        et_userName.setText(userNameInsAd);
    }

    public void btn_on_click(View view) {
        final String etaddressEdited = et_addressEdited.getText().toString().trim();
        final String etusername = et_userName.getText().toString().trim();
        Intent intent = getIntent();
        final String userNameInsAd = intent.getStringExtra("passValueForInsAd");
        if (TextUtils.isEmpty(etusername)) {
            ToastUtils.showMsg(this, "请输入账户");
            et_userName.requestFocus();
        } else if (TextUtils.isEmpty(etaddressEdited)) {
            ToastUtils.showMsg(this, "请输入地址");
            et_addressEdited.requestFocus();
        } else {
            final EntityUserEntity entityUserEntity = new EntityUserEntity();
            entityUserEntity.setAddress(etaddressEdited);
            entityUserEntity.setUserName(etusername);
            Log.i("————————————————", "已登陆：" + userNameInsAd);
            Log.i("————————————————", "输入：" + etusername);
            if (userNameInsAd == null) {
                ToastUtils.showMsg(AddressInsActivity.this, "未登录");
                intent = new Intent(AddressInsActivity.this, LoginActivity.class);
                startActivity(intent);
            } else if (userNameInsAd.equals(etusername)) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        AddressDao.insAddress(etusername, etaddressEdited);
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.showMsg(AddressInsActivity.this, "添加成功");
                                Intent intent = new Intent(AddressInsActivity.this, AddressActivity.class);
                                intent.putExtra("passValue2", userNameInsAd);
                                startActivity(intent);
                                setResult(1);
                                finish();
                            }
                        });
                    }
                }).start();
            } else {
                ToastUtils.showMsg(AddressInsActivity.this, "无权限，请检查用户名");
            }

        }
    }

}