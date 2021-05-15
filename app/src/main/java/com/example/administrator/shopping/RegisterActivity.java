package com.example.administrator.shopping;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
    private CheckBox cb_DisplayPassword2;

    public static final String TAG = "OUTPUT";
    public Button btn_Register;
    private Handler mainHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        SettingActivity.activityList.add(this);
        mainHandler = new Handler(getMainLooper());

        et_phone = findViewById(R.id.et_phone);
        et_userName = findViewById(R.id.et_LoginName);
        et_newPassword = findViewById(R.id.et_newPassword);
        et_phone = findViewById(R.id.et_phone);
        //et_age = findViewById(R.id.et_age);
        et_sex = findViewById(R.id.et_sex);
        et_area = findViewById(R.id.et_area);
        et_realName = findViewById(R.id.et_realName);

        cb_DisplayPassword2 = findViewById(R.id.cb_DisplayPassword2);

        go_back = findViewById(R.id.go_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cb_DisplayPassword2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d(TAG, "onCheckedChanged: " + isChecked);
                if (isChecked) {
                    //选择状态 显示明文--设置为可见的密码
                    et_newPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //默认状态显示密码--设置文本 要一起写才能起作用 InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
                    et_newPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    public void btn_on_click(View view) {
        final String etuserName = et_userName.getText().toString().trim();
        final String etnewPassword = et_newPassword.getText().toString().trim();
        final String etarea = et_area.getText().toString().trim();
        //final String etage = et_age.getText().toString().trim();
        final String etsex = et_sex.getText().toString().trim();
        final String etphone = et_phone.getText().toString().trim();
        final String etrealName = et_realName.getText().toString().trim();


        if (TextUtils.isEmpty(etuserName)) {
            ToastUtils.showMsg(this, "请输入用户名");
            et_userName.requestFocus();
        } else if (TextUtils.isEmpty(etnewPassword)) {
            ToastUtils.showMsg(this, "请输入用户密码");
            et_newPassword.requestFocus();
        } else if (TextUtils.isEmpty(et_phone.getText().toString().trim())) {
            ToastUtils.showMsg(this, "请输入您的电话号码");
            et_phone.requestFocus();
        } else {
            final EntityUserEntity entityUserEntity = new EntityUserEntity();
            entityUserEntity.setUserName(etuserName);
            entityUserEntity.setPassword(etnewPassword);
            entityUserEntity.setRealName(etrealName);
            //entityUserEntity.setAge(etage);
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
                            if (et_phone.getText().toString().trim().length() != 11) {
                                ToastUtils.showMsg(RegisterActivity.this, "您的电话号码位数不正确");
                                et_phone.requestFocus();
                            } else {
                                String phone_number = et_phone.getText().toString().trim();
                                String num = "[1][35789]\\d{9}";
                                if (phone_number.matches(num)) {
                                    ToastUtils.showMsg(RegisterActivity.this, "注册成功,即将登录");
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    setResult(1);
                                    finish();
                                } else {
                                    ToastUtils.showMsg(RegisterActivity.this, "请输入正确的手机号码");
                                }
                            }

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