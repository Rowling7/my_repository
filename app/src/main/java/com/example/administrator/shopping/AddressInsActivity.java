package com.example.administrator.shopping;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.shopping.dao.EntityUserDao;
import com.example.administrator.shopping.entity.EntityUserEntity;
import com.example.administrator.shopping.utils.ToastUtils;

public class AddressInsActivity extends AppCompatActivity {

    private Button btn_insAddress;
    private EditText et_addressEdited;
    private EntityUserEntity addressEdit;//用户要修改的地址
    private Handler mainHandler;
    private EntityUserDao entityUserDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_ins);

        btn_insAddress = findViewById(R.id.btn_insAddress);
    }

    /*public void btn_on_click(View view) {
        final String etaddressEdited = et_addressEdited.getText().toString().trim();
        if (TextUtils.isEmpty(etaddressEdited)) {
            ToastUtils.showMsg(this, "请输入地址或返回");
            et_addressEdited.requestFocus();
        } else {
            final EntityUserEntity entityUserEntity = new EntityUserEntity();
            entityUserEntity.setAddress(etaddressEdited);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    EntityUserDao.insAddress(entityUserEntity);
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.showMsg(AddressInsActivity.this, "添加成功");
                            Intent intent = new Intent(AddressInsActivity.this, AddressActivity.class);
                            startActivity(intent);
                            setResult(1);
                            finish();
                        }
                    });
                }
            }).start();
        }
    }*/

}