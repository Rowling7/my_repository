package com.example.administrator.shopping;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class PayPasswordDialog extends Dialog implements View.OnClickListener {
    Context context;
    private TextView tvReturn;
    private PayPasswordView payPassword;
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private TextView tv5;
    private TextView tv6;
    private TextView tv7;
    private TextView tv8;
    private TextView tv9;
    private TextView tv;
    private TextView tvDel;

    public PayPasswordDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_pay_pwd, null);
        setContentView(view);
        initView();

        Window window = getWindow();
        WindowManager.LayoutParams mParams = window.getAttributes();
        mParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setGravity(Gravity.BOTTOM);
        window.setAttributes(mParams);
        setCanceledOnTouchOutside(true);


        tvReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        tvDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payPassword.delLastPassword();
            }
        });
        payPassword.setPayPasswordEndListener(new PayPasswordView.PayEndListener() {
            @Override
            public void doEnd(String password) {
                if (dialogClick != null) {
                    dialogClick.doConfirm(password);
                }
            }
        });
        tv.setOnClickListener(this);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);
        tv5.setOnClickListener(this);
        tv6.setOnClickListener(this);
        tv7.setOnClickListener(this);
        tv8.setOnClickListener(this);
        tv9.setOnClickListener(this);

    }

    private void initView() {
        tvReturn = findViewById(R.id.tv_return);
        payPassword = findViewById(R.id.pay_password);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        tv4 = findViewById(R.id.tv4);
        tv5 = findViewById(R.id.tv5);
        tv6 = findViewById(R.id.tv6);
        tv7 = findViewById(R.id.tv7);
        tv8 = findViewById(R.id.tv8);
        tv9 = findViewById(R.id.tv9);
        tv = findViewById(R.id.tv);
        tvDel = findViewById(R.id.tv_del);
    }

    DialogClick dialogClick;

    public void setDialogClick(DialogClick dialogClick) {
        this.dialogClick = dialogClick;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv:
                payPassword.addPassword("0");
                break;
            case R.id.tv1:
                payPassword.addPassword("1");
                break;
            case R.id.tv2:
                payPassword.addPassword("2");
                break;
            case R.id.tv3:
                payPassword.addPassword("3");
                break;
            case R.id.tv4:
                payPassword.addPassword("4");
                break;
            case R.id.tv5:
                payPassword.addPassword("5");
                break;
            case R.id.tv6:
                payPassword.addPassword("6");
                break;
            case R.id.tv7:
                payPassword.addPassword("7");
                break;
            case R.id.tv8:
                payPassword.addPassword("8");
                break;
            case R.id.tv9:
                payPassword.addPassword("9");
                break;
        }
    }

    public interface DialogClick {
        void doConfirm(String password);
    }

}
