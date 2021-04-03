package com.example.administrator.shopping;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
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
import android.widget.TextView;

import com.example.administrator.shopping.dao.EntityUserDao;
import com.example.administrator.shopping.entity.EntityUserEntity;
import com.example.administrator.shopping.utils.ToastUtils;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_UserName;
    private EditText et_Password;
    private CheckBox cb_DisplayPassword;
    private Button btn_Login;
    private TextView tvRegister;
    public Intent intent = null;
    private Handler mainHandler;     // 主线程
    private ImageView go_back;
    public static final String TAG = "OUTPUT";
    private final Handler handler = new Handler() {
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();

        SettingActivity.activityList.add(this);
        cb_DisplayPassword = findViewById(R.id.cb_DisplayPassword);

        btn_Login = findViewById(R.id.btn_Login);
        et_UserName = findViewById(R.id.et_userName);
        et_Password = findViewById(R.id.et_password);

        //跳转到注册
        tvRegister = findViewById(R.id.tv_register);
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


        go_back = findViewById(R.id.go_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_Login.setOnClickListener(this);

        cb_DisplayPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d(TAG, "onCheckedChanged: " + isChecked);
                if (isChecked) {
                    //选择状态 显示明文--设置为可见的密码
                    et_Password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //默认状态显示密码--设置文本 要一起写才能起作用 InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
                    et_Password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

    }


    private void initView() {
        SettingActivity.activityList.add(this);
        mainHandler = new Handler(getMainLooper());//获取主线程
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Login:    // 登录按钮
                doLogin();
                break;
        }
    }

    private void doLogin() {
        final String UserName = et_UserName.getText().toString().trim();//Loginname传值到下面，et_LoginName xml里的值
        final String Password = et_Password.getText().toString().trim();

        if (TextUtils.isEmpty(UserName)) {
            ToastUtils.showMsg(this, "请输入用户名");
            et_UserName.requestFocus();
        } else if (TextUtils.isEmpty(Password)) {
            ToastUtils.showMsg(this, "请输入用户密码");
            et_Password.requestFocus();
        } else {
            new Thread(new Runnable() {//子线程
                @Override
                public void run() {
                    final EntityUserEntity entityUserEntity = EntityUserDao.login(UserName, Password);
                    mainHandler.post(new Runnable() {//回到主线程提示土司消息
                        @Override
                        public void run() {
                            if (entityUserEntity == null) {
                                ToastUtils.showMsg(LoginActivity.this, "用户名或密码错误");
                            } else {
                                ToastUtils.showMsg(LoginActivity.this, "登录成功，欢迎！");
                                Intent intent = new Intent(LoginActivity.this, MyActivity.class);
                                intent.putExtra("passValue", et_UserName.getText().toString());//传递“id”至MyActivity
                                startActivity(intent);

                            }
                        }
                    });
                }
            }).start();
        }
    }
}