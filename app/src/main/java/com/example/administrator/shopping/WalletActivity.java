package com.example.administrator.shopping;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.shopping.dao.AddressDao;
import com.example.administrator.shopping.dao.EntityUserDao;
import com.example.administrator.shopping.entity.EntityUserEntity;
import com.example.administrator.shopping.utils.ToastUtils;

public class WalletActivity extends AppCompatActivity {
    private TextView btn_pay;
    private TextView tv_money;
    private Button btn_20;
    private Button btn_50;
    private Button btn_100;
    private Button btn_150;
    private Button btn_200;
    private Button btn_500;
    private EditText et_jine;
    private TextView tv_wallet;
    private ImageView go_back;

    public static final String TAG = "OUTPUT";
    private EntityUserDao entityUserDao;
    private EntityUserEntity entityUserEntity;
    private Handler mainHandler;
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            //super.handleMessage(msg);
            if (msg.what == 0) {
                String walletgeneral = (String) msg.obj;
                if (walletgeneral == null) {
                    tv_wallet.setText("0.00");
                } else
                    tv_wallet.setText(walletgeneral);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        initView();

        tv_money = findViewById(R.id.tv_money);
        btn_20 = findViewById(R.id.btn_20);
        btn_20.setOnClickListener(onclicklistener);
        btn_50 = findViewById(R.id.btn_50);
        btn_50.setOnClickListener(onclicklistener);
        btn_100 = findViewById(R.id.btn_100);
        btn_100.setOnClickListener(onclicklistener);
        btn_150 = findViewById(R.id.btn_150);
        btn_150.setOnClickListener(onclicklistener);
        btn_200 = findViewById(R.id.btn_200);
        btn_200.setOnClickListener(onclicklistener);
        btn_500 = findViewById(R.id.btn_500);
        btn_500.setOnClickListener(onclicklistener);
        et_jine = findViewById(R.id.et_jine);
        et_jine.setOnClickListener(onclicklistener);
        tv_wallet=findViewById(R.id.tv_wallet);


        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PayPasswordDialog dialog = new PayPasswordDialog(WalletActivity.this, R.style.mydialog);
                dialog.setDialogClick(new PayPasswordDialog.DialogClick() {
                    @Override
                    public void doConfirm(String password) {

                        final String etjine = et_jine.getText().toString().trim();
                        final String jine = tv_money.getText().toString().trim();
                        Log.i(TAG, "doConfirm: "+jine);
                        final EntityUserEntity entityUserEntity = new EntityUserEntity();
                        entityUserEntity.setWallet(jine);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = getIntent();
                                final String userName = intent.getStringExtra("passValue");//登陆后的传值
                                final int iRow = EntityUserDao.addWallet(userName,jine);
                                dialog.dismiss();

                               /* intent = new Intent(WalletActivity.this, MyActivity.class);
                                doQueryWallet();
                                */
                                ToastUtils.showMsg(WalletActivity.this,"充值成功");

                                finish();
                              /*  mainHandler.post(new Runnable() {
                                    @Override
                                    public void run() {


                                    }
                                });*/
                            }
                        }).start();



                    }
                });
                dialog.show();
            }
        });



        go_back = findViewById(R.id.go_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    // 执行查询用户数量的方法
    public void doQueryWallet() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                Intent intent = getIntent();
                final String userName2 = intent.getStringExtra("passValue");//登陆后的传值
                String userWallet = EntityUserDao.getUserWallet(userName2);

                Message msg = Message.obtain();
                msg.what = 0;   // 查询结果
                msg.obj = userWallet;
                // 向主线程发送数据
                handler.sendMessage(msg);
            }

        }).start();
    }

    public View.OnClickListener onclicklistener = new View.OnClickListener() {

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_20:
                    tv_money.setText("20");
                    break;
                case R.id.btn_50:
                    tv_money.setText("50");
                    break;
                case R.id.btn_100:
                    tv_money.setText("100");
                    break;
                case R.id.btn_150:
                    tv_money.setText("150");
                    break;
                case R.id.btn_200:
                    tv_money.setText("200");
                    break;
                case R.id.btn_500:
                    tv_money.setText("500");
                    break;
                case R.id.et_jine:
                    tv_money.setText(et_jine.getText());
                    break;
            }
        }
    };


    private void initView() {

        btn_pay = findViewById(R.id.btn_pay);
    }
}