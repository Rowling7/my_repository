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
import com.example.administrator.shopping.utils.ToastUtils;

public class EditPhoneActivity extends AppCompatActivity {
    private EditText et_editPhone;
    private Button btn_edit;
    private ImageView go_back;
    private Handler mainHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_phone);
        SettingActivity.activityList.add(this);//用来退出应用

        final Intent intent = getIntent();
        final String keyForEdit = intent.getStringExtra("passValueForEdit");//MyActivity的传值

        go_back = findViewById(R.id.go_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        et_editPhone = findViewById(R.id.et_editPhone);
        btn_edit = findViewById(R.id.btn_edit);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doEdit();

            }
        });
        initView();
    }

    private void initView() {

        mainHandler = new Handler(getMainLooper());//获取主线程
    }

    public void doEdit() {
        final Intent intent = getIntent();
        final String keyForEdit = intent.getStringExtra("passValueForEdit");//MyActivity的传值
        final String newPhone = et_editPhone.getText().toString().trim();

        if (TextUtils.isEmpty(newPhone)) {
            ToastUtils.showMsg(EditPhoneActivity.this, "请输入新的手机号");
            et_editPhone.requestFocus();
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Intent intent = getIntent();
                    final int iRow = EntityUserDao.updatePhone(keyForEdit, newPhone);
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (et_editPhone.getText().toString().trim().length() != 11) {
                                ToastUtils.showMsg(EditPhoneActivity.this, "您的电话号码位数不正确");
                                et_editPhone.requestFocus();
                            } else {
                                String phone_number = et_editPhone.getText().toString().trim();
                                String num = "[1][35789]\\d{9}";
                                if (phone_number.matches(num)) {
                                    ToastUtils.showMsg(EditPhoneActivity.this, "更改成功~~");
                                    setResult(1);   // 使用参数表示当前界面操作成功，并返回管理界面
                                    finish();
                                } else {
                                    ToastUtils.showMsg(EditPhoneActivity.this, "请输入正确的手机号码");
                                }
                            }
                        }
                    });
                }
            }).start();
        }
    }
}