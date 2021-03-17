package com.example.administrator.shopping;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.administrator.shopping.dao.EntityUserDao;
import com.example.administrator.shopping.entity.EntityUserEntity;
import com.example.administrator.shopping.utils.Myuntils;
import com.example.administrator.shopping.utils.ToastUtils;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et_phone;
    private EditText et_userName;
    private EditText et_newPassword;
    private EditText et_age;
    private EditText et_area;
    private EditText et_sex;
    private EditText et_realName;
    private ImageView go_back;
    //private EditText et_address;

    public Button btn_Register;
    private Handler mainHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        SettingActivity.activityList.add(this);

        et_phone = findViewById(R.id.et_phone);
        et_userName = findViewById(R.id.et_LoginName);
        et_newPassword = findViewById(R.id.et_newPassword);
        et_phone = findViewById(R.id.et_phone);
        et_age = findViewById(R.id.et_age);
        et_sex = findViewById(R.id.et_sex);
        et_area = findViewById(R.id.et_area);
        et_realName = findViewById(R.id.et_realName);
        //et_address=findViewById(R.id.et_address);
        mainHandler = new Handler(getMainLooper());

        go_back = findViewById(R.id.go_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void btn_on_click(View view) {
        final String etuserName = et_userName.getText().toString().trim();
        final String etnewPassword = et_newPassword.getText().toString().trim();
        final String etarea = et_area.getText().toString().trim();
        final String etage = et_age.getText().toString().trim();
        final String etsex = et_sex.getText().toString().trim();
        final String etphone = et_phone.getText().toString().trim();
        final String etrealName = et_realName.getText().toString().trim();
        //final String etadress = et_address.getText().toString().trim();

        if (TextUtils.isEmpty(etuserName)) {
            ToastUtils.showMsg(this, "请输入用户名");
            et_userName.requestFocus();
        } else if (TextUtils.isEmpty(etuserName)) {
            ToastUtils.showMsg(this, "请输入用户密码");
            et_newPassword.requestFocus();
        } else {
            final EntityUserEntity entityUserEntity = new EntityUserEntity();
            entityUserEntity.setUserName(etuserName);
            entityUserEntity.setPassword(etnewPassword);
            entityUserEntity.setRealName(etrealName);
            entityUserEntity.setAge(etage);
            entityUserEntity.setSex(etsex);
            entityUserEntity.setPhone(etphone);
            entityUserEntity.setArea(etarea);
            entityUserEntity.setCreate_time(Myuntils.getDateStrFromNow());
            //entityUserEntity.setAddress(etadress);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    EntityUserDao.insertUser(entityUserEntity);
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.showMsg(RegisterActivity.this, "注册成功,即将登录");
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                            setResult(1);
                            finish();
                        }
                    });
                }
            }).start();
        }
    }

    @Override
    public void onClick(View view) {

    }
}