package com.example.administrator.shopping;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.example.administrator.shopping.dao.EntityUserDao;
import com.example.administrator.shopping.entity.EntityUserEntity;
import com.example.administrator.shopping.utils.ToastUtils;

public class AddressEditActity extends AppCompatActivity {


    private EditText et_addressEdited;
    private EntityUserEntity addressEdit;//用户要修改的地址
    private Handler mainHandler;
    private EntityUserDao entityUserDao;

    Intent intent = getIntent();
    final String userNameForEditAd = intent.getStringExtra("passValueForEditAd");//登陆后的传值


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_edit);

        et_addressEdited = findViewById(R.id.et_addressEdited);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            addressEdit = (EntityUserEntity) bundle.getSerializable("userEdit");

            et_addressEdited.setText(addressEdit.getAddress());
        }

        entityUserDao = new EntityUserDao();
        mainHandler = new Handler(getMainLooper());
    }

    // 确定按钮的点击事件处理
    public void btn_on_click(View view) {
        final String newAddress = et_addressEdited.getText().toString().trim();
        if (TextUtils.isEmpty(newAddress)) {
            ToastUtils.showMsg(this, "请输入地址或返回");
            et_addressEdited.requestFocus();
        } else {
            addressEdit.setAddress(newAddress);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final int iRow = EntityUserDao.updateAddress(addressEdit);
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            setResult(1);   // 使用参数表示当前界面操作成功，并返回管理界面
                            finish();
                        }
                    });
                }
            }).start();
        }
    }
}