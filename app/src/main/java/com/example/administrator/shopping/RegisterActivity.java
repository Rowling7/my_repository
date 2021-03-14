package com.example.administrator.shopping;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.shopping.Dao.EntityUserDao;
import com.example.administrator.shopping.Entity.EntityUserEntity;
import com.example.administrator.shopping.utils.ToastUtils;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    public EditText et_phone;
    public EditText et_userName;
    public EditText et_newPassword;
    public EditText et_sex;
    public EditText et_adderss;
    public EditText et_age;
    public EditText et_area;

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
        et_adderss = findViewById(R.id.et_address);
        et_age = findViewById(R.id.et_age);
        et_sex = findViewById(R.id.et_sex);
        // et_area = findViewById(R.id.et_area);
    }

    public void btn_on_click(View view) {
        final String etuserName = et_userName.getText().toString().trim();
        final String etnewPassword = et_newPassword.getText().toString().trim();
        final String etaddress = et_adderss.getText().toString().trim();
        final String etage = et_age.getText().toString().trim();
        final String etsex = et_sex.getText().toString().trim();
        final String etphone = et_phone.getText().toString().trim();
        //final String etarea = et_area.getText().toString().trim();

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
            entityUserEntity.setAddress(etaddress);
            entityUserEntity.setAge(etage);
            entityUserEntity.setSex(etsex);
            entityUserEntity.setPhone(etphone);
            // entityUserEntity.setArea(etarea);

            new Thread(new Runnable(){
                @Override
                public void run() {
                    EntityUserDao.insertUser(entityUserEntity);
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            /*ToastUtils.showMsg(RegisterActivity.this, "注册成功");
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);*/
                            //setResult(1);
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