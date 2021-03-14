package com.example.administrator.shopping;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.shopping.utils.ToastUtils;

public class CustomerActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_name;
    private Handler handler;
    private TextView tvUpdate;
    private TextView tv_userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        initView();

        //检查更新

        /* tvUpdate = findViewById(R.id.tv_update);
        tvUpdate.setOnClickListener(new View.OnClickListener() { //弹出消息
            @Override
            public void onClick(View view) {
                ToastUtils.showMsg(CustomerActivity.this, "正在检查更新！");
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showMsg(CustomerActivity.this, "已是最新版本！");
                    }
                }, 3000);
            }
        });*/
    }

    private void initView() {

    }

    @Override
    public void onClick(View view) {

    }
}