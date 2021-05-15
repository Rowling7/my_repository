package com.example.administrator.shopping;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.administrator.shopping.dao.EntityUserDao;
import com.example.administrator.shopping.entity.EntityUserEntity;
import com.example.administrator.shopping.utils.Myuntils;
import com.example.administrator.shopping.utils.ToastUtils;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, DatePicker.OnDateChangedListener {
    private EditText et_phone;
    private EditText et_userName;
    private EditText et_newPassword;
    private TextView et_age;
    private EditText et_area;
    private EditText et_sex;
    private EditText et_realName;
    private ImageView go_back;
    private CheckBox cb_DisplayPassword2;

    private Context context;
    private LinearLayout llDate;
    private TextView tvDate;
    private int year, month, day, hour, minute;
    //在TextView上显示的字符
    private StringBuffer date, time;

    public static final String TAG = "OUTPUT";
    public Button btn_Register;
    private Handler mainHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        SettingActivity.activityList.add(this);
        mainHandler = new Handler(getMainLooper());

        et_phone = findViewById(R.id.et_phone);
        et_userName = findViewById(R.id.et_LoginName);
        et_newPassword = findViewById(R.id.et_newPassword);
        et_phone = findViewById(R.id.et_phone);
        et_age = findViewById(R.id.tv_date);
        et_sex = findViewById(R.id.et_sex);
        et_area = findViewById(R.id.et_area);
        et_realName = findViewById(R.id.et_realName);

        context = this;
        date = new StringBuffer();
        time = new StringBuffer();
        initView();
        initDateTime();

        cb_DisplayPassword2 = findViewById(R.id.cb_DisplayPassword2);

        go_back = findViewById(R.id.go_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cb_DisplayPassword2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d(TAG, "onCheckedChanged: " + isChecked);
                if (isChecked) {
                    //选择状态 显示明文--设置为可见的密码
                    et_newPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //默认状态显示密码--设置文本 要一起写才能起作用 InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
                    et_newPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    private void initView() {

        llDate = findViewById(R.id.ll_date);
        tvDate = findViewById(R.id.tv_date);
        llDate.setOnClickListener(this);

    }

    private void initDateTime() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_date:
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (date.length() > 0) { //清除上次记录的日期
                            date.delete(0, date.length());
                        }
                        tvDate.setText(date.append(String.valueOf(year)).append("-").append(String.valueOf(month)).append("-").append(day));
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                final AlertDialog dialog = builder.create();
                View dialogView = View.inflate(context, R.layout.dialog_date, null);
                final DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.datePicker);

                dialog.setTitle("设置日期");
                dialog.setView(dialogView);
                dialog.show();
                //初始化日期监听事件
                datePicker.init(year, month - 1, day, this);
                break;

        }
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        this.year = year;
        this.month = monthOfYear;
        this.day = dayOfMonth;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void btn_on_click(View view) {
        final String etuserName = et_userName.getText().toString().trim();
        final String etnewPassword = et_newPassword.getText().toString().trim();
        final String etarea = et_area.getText().toString().trim();
        final String etage = tvDate.getText().toString().trim();
        final String etsex = et_sex.getText().toString().trim();
        final String etphone = et_phone.getText().toString().trim();
        final String etrealName = et_realName.getText().toString().trim();

        Log.i(TAG, "btn_on_click: " + etage);
        if (TextUtils.isEmpty(etuserName)) {
            ToastUtils.showMsg(this, "请输入用户名");
            et_userName.requestFocus();
        } else if (TextUtils.isEmpty(etnewPassword)) {
            ToastUtils.showMsg(this, "请输入用户密码");
            et_newPassword.requestFocus();
        } else if (TextUtils.isEmpty(et_phone.getText().toString().trim())) {
            ToastUtils.showMsg(this, "请输入您的电话号码");
            et_phone.requestFocus();
        } else {
            final EntityUserEntity entityUserEntity = new EntityUserEntity();
            entityUserEntity.setUserName(etuserName);
            entityUserEntity.setPassword(etnewPassword);
            entityUserEntity.setRealName(etrealName);
            entityUserEntity.setBirthday(etage);
            entityUserEntity.setSex(etsex);
            entityUserEntity.setPhone(etphone);
            entityUserEntity.setArea(etarea);
            entityUserEntity.setCreate_time(Myuntils.getDateStrFromNow());
            //entityUserEntity.setAddress(etadress);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    EntityUserDao.insertUser(entityUserEntity);
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (et_phone.getText().toString().trim().length() != 11) {
                                ToastUtils.showMsg(RegisterActivity.this, "您的电话号码位数不正确");
                                et_phone.requestFocus();
                            } else {
                                String phone_number = et_phone.getText().toString().trim();
                                String num = "[1][35789]\\d{9}";
                                if (phone_number.matches(num)) {
                                    ToastUtils.showMsg(RegisterActivity.this, "注册成功,即将登录");
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    setResult(1);
                                    finish();
                                } else {
                                    ToastUtils.showMsg(RegisterActivity.this, "请输入正确的手机号码");
                                }
                            }

                        }
                    });
                }
            }).start();
        }


    }
}