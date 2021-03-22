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

import com.example.administrator.shopping.dao.AddressDao;
import com.example.administrator.shopping.dao.OrderDao;
import com.example.administrator.shopping.entity.AddressEntity;
import com.example.administrator.shopping.entity.OrderEntity;
import com.example.administrator.shopping.utils.ToastUtils;

public class SetpingjiaActivity extends AppCompatActivity {

    private ImageView go_back;
    private EditText et_pingjiaEdited;
    private Button btn_edit;

    private Handler mainHandler;
    private OrderEntity descriptionEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setpingjia);

        SettingActivity.activityList.add(this);//用来退出



        et_pingjiaEdited=findViewById(R.id.et_pingjiaEdited);
        btn_edit=findViewById(R.id.btn_edit);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            descriptionEdit = (OrderEntity) bundle.getSerializable("descriptionEdit");

            et_pingjiaEdited.setText(descriptionEdit.getDescription());
        }


        Intent intent = getIntent();
        final String passValue = intent.getStringExtra("passValue");//登陆后的传值
        final String uuid = intent.getStringExtra("uuid");//登陆后的传值

        go_back = findViewById(R.id.go_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mainHandler = new Handler(getMainLooper());


    }


    // 确定按钮的点击事件处理
    public void btn_on_click_forpingjia(View view) {
        final String description = et_pingjiaEdited.getText().toString().trim();

        if (TextUtils.isEmpty(description)) {
            ToastUtils.showMsg(this, "请输入地址或返回");
            et_pingjiaEdited.requestFocus();
        } else {
            descriptionEdit.setDescription(description);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Intent intent = getIntent();
                    final int iRow = OrderDao.setDescription(descriptionEdit);
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.showMsg(SetpingjiaActivity.this, "评论成功~~");
                            setResult(1);
                            finish();
                        }
                    });
                }
            }).start();

        }
    }
}