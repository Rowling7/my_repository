package com.example.administrator.shopping;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.shopping.entity.OrderEntity;
import com.example.administrator.shopping.utils.ToastUtils;

public class KefuActivity extends AppCompatActivity {


    private ImageView go_back;
    private TextView et_dingdanhao;
    private OrderEntity orderUuid;
    private Button btn_tijiaokefu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kefu);


        go_back = findViewById(R.id.go_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                final String passValue = intent.getStringExtra("passValue");//登陆后的传值
                intent =new Intent(KefuActivity.this,ReceivedActivity.class);
                intent.putExtra("passValue",passValue);
                startActivity(intent);
            }
        });


        et_dingdanhao = findViewById(R.id.et_dingdanhao);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            orderUuid = (OrderEntity) bundle.getSerializable("userEdit");
            et_dingdanhao.setText(orderUuid.getUuid()+"");
        }


        btn_tijiaokefu=findViewById(R.id.btn_tijiaokefu);
        btn_tijiaokefu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                final String passValue = intent.getStringExtra("passValue");//登陆后的传值
                intent =new Intent(KefuActivity.this,ReceivedActivity.class);
                intent.putExtra("passValue",passValue);
                ToastUtils.showMsg(KefuActivity.this,"已提交客服，请等待回复");
                startActivity(intent);
            }
        });

    }
}