package com.example.administrator.shopping;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.shopping.dao.EntityUserDao;
import com.example.administrator.shopping.database.DbOpenHelper;
import com.example.administrator.shopping.utils.ToastUtils;
import com.mysql.jdbc.PreparedStatement;

import java.io.ByteArrayOutputStream;
import java.sql.SQLException;


import static com.example.administrator.shopping.database.DbOpenHelper.getConnection;

public class MyDetailsActivity extends AppCompatActivity {


    private TextView tv_sex;
    private TextView tv_age;
    private TextView tv_phone;
    private TextView tv_area;
    private TextView tv_name;
    private ImageView go_back;
    private ImageView img_head;
    private TextView tv_wallet;
    private Handler mainHandler;     // 主线程

    Uri uri;//头像
    String imageString;

    public static final String TAG = "OUTPUT";

    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 0) {
                String uAge = (String) msg.obj;
                tv_age.setText(uAge);
            } else if (msg.what == 1) {
                String uSex = (String) msg.obj;
                tv_sex.setText(uSex);
            } else if (msg.what == 2) {
                String uPhone = (String) msg.obj;
                tv_phone.setText(uPhone);
            } else if (msg.what == 3) {
                String uArea = (String) msg.obj;
                tv_area.setText(uArea);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_details);
        SettingActivity.activityList.add(this);

        initView();


        tv_age = findViewById(R.id.tv_age);
        /*tv_age.setOnClickListener(onclicklistener);*/
        tv_phone = findViewById(R.id.tv_phone);
        tv_phone.setOnClickListener(onclicklistener);
        tv_area = findViewById(R.id.tv_area);
        tv_area.setOnClickListener(onclicklistener);
        tv_sex = findViewById(R.id.tv_sex);
        tv_sex.setOnClickListener(onclicklistener);

        dohead();
        selectAge();
        selectSex();
        selectPhone();
        selectArea();

        tv_name = findViewById(R.id.tv_name);
        final Intent intent = getIntent();
        final String userNameForInfo = intent.getStringExtra("passValueForUser");//MyActivity的传值
        tv_name.setText(userNameForInfo);

        go_back = findViewById(R.id.go_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                final String userNameForInfo = intent.getStringExtra("passValueForUser");//MyActivity的传值
                intent = new Intent(MyDetailsActivity.this, MyActivity.class);
                intent.putExtra("passValue", userNameForInfo);
                startActivity(intent);
                //finish();
            }
        });


        //主线程使用网络请求
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        img_head = findViewById(R.id.img_head);
        //选择本地图片
        img_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
                galleryIntent.setType("image/*");//图片
                startActivityForResult(galleryIntent, 0);
            }
        });

    }


    public View.OnClickListener onclicklistener = new View.OnClickListener() {

        public void onClick(View v) {
            Intent intent = getIntent();
            final String userNameForEdit = intent.getStringExtra("passValueForUser");
            switch (v.getId()) {

                /*case R.id.tv_age:
                    intent = new Intent(MyDetailsActivity.this, EditAgeActivity.class);
                    intent.putExtra("passValueForEdit", userNameForEdit);
                    break;*/
                case R.id.tv_phone:
                    intent = new Intent(MyDetailsActivity.this, EditPhoneActivity.class);
                    intent.putExtra("passValueForEdit", userNameForEdit);
                    break;
                case R.id.tv_sex:
                    intent = new Intent(MyDetailsActivity.this, EditSexActivity.class);
                    intent.putExtra("passValueForEdit", userNameForEdit);
                    break;
                case R.id.tv_area:
                    intent = new Intent(MyDetailsActivity.this, EditAreaActivity.class);
                    intent.putExtra("passValueForEdit", userNameForEdit);
                    break;
            }
            startActivity(intent);
        }
    };

    private void initView() {
        mainHandler = new Handler(getMainLooper());//获取主线程
    }


    public void dohead() {
        img_head = findViewById(R.id.img_head);
        Intent intent = getIntent();
        final String userName = intent.getStringExtra("passValueForUser");//登陆后的传值
        String imgHead = EntityUserDao.getHead(userName);
        byte[] imageBytes = Base64.decode(imgHead, Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        img_head.setImageBitmap(decodedImage);

    }

    // 查询age
    public void selectAge() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent intent = getIntent();
                final String userNameForDetails = intent.getStringExtra("passValueForUser");//MyActivity的传值
                Log.i(TAG, "用户名：" + userNameForDetails);
                String age = EntityUserDao.getUserAge(userNameForDetails);
                Message msg = Message.obtain();
                msg.what = 0;   // 查询结果
                msg.obj = age;
                // 向主线程发送数据
                handler.sendMessage(msg);


            }
        }).start();
    }

    // 查询sex
    public void selectSex() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent intent = getIntent();
                final String userNameForDetails = intent.getStringExtra("passValueForUser");//MyActivity的传值
                String sex = EntityUserDao.getUserSex(userNameForDetails);
                Message msg = Message.obtain();
                msg.what = 1;   // 查询结果
                msg.obj = sex;
                // 向主线程发送数据
                handler.sendMessage(msg);


            }
        }).start();
    }

    // 查询phone
    public void selectPhone() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent intent = getIntent();
                final String userNameForDetails = intent.getStringExtra("passValueForUser");//MyActivity的传值
                String phone = EntityUserDao.getUserPhone(userNameForDetails);
                Message msg = Message.obtain();
                msg.what = 2;   // 查询结果
                msg.obj = phone;
                // 向主线程发送数据
                handler.sendMessage(msg);

            }
        }).start();
    }

    // 查询area
    public void selectArea() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent intent = getIntent();
                final String userNameForDetails = intent.getStringExtra("passValueForUser");//MyActivity的传值
                String area = EntityUserDao.getUserArea(userNameForDetails);
                Message msg = Message.obtain();
                msg.what = 3;   // 查询结果
                msg.obj = area;
                // 向主线程发送数据
                handler.sendMessage(msg);
            }
        }).start();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // TODO Auto-generated method stub
        if (requestCode == 0 && resultCode == -1) {
            uri = data.getData();
            img_head.setImageURI(uri);

            //将图片转换成Base64编码
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageBytes = baos.toByteArray();
                imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                Intent intent = getIntent();
                final String userName = intent.getStringExtra("passValueForUser");//MyActivity的传值
                EntityUserDao.insertHead(imageString, userName);

                ToastUtils.showMsg(this, "上传成功！");
            } catch (Exception e) {
            }
        }

    }


}