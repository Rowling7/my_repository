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

public class EditSexActivity extends AppCompatActivity {
    private EditText et_editSex;
    private Button btn_edit;
    private ImageView go_back;
    private Handler mainHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_sex);
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

        et_editSex=findViewById(R.id.et_editSex);
        btn_edit=findViewById(R.id.btn_edit);
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

    public void  doEdit(){
        final Intent intent = getIntent();
        final String keyForEdit = intent.getStringExtra("passValueForEdit");//MyActivity的传值
        final String newSex = et_editSex.getText().toString().trim();

        if (TextUtils.isEmpty(newSex)) {
            ToastUtils.showMsg(EditSexActivity.this, "请输入新的手机号或返回");
            et_editSex.requestFocus();
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Intent intent = getIntent();
                    final int iRow = EntityUserDao.updateSex(keyForEdit,newSex);
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.showMsg(EditSexActivity.this, "更改成功~~");
                            setResult(1);   // 使用参数表示当前界面操作成功，并返回管理界面
                            finish();
                        }
                    });
                }
            }).start();
        }
    }
}